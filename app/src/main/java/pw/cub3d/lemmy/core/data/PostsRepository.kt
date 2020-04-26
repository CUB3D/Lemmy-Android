package pw.cub3d.lemmy.core.data

import android.media.ThumbnailUtils
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import pw.cub3d.lemmy.core.networking.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import javax.inject.Inject
import javax.inject.Singleton

class PostsRepository @Inject constructor(
    private val lemmyApiInterface: LemmyApiInterface,
    private val authRepository: AuthRepository
) {
    private var page = 1

    private val mutableLiveData = MutableLiveData<List<PostView>>()

    fun getPosts(community: Int? = null): LiveData<List<PostView>> {
        page = 1
        getCurrentPage(community)

        return mutableLiveData
    }

    fun getCurrentPage(community: Int? = null) {
        println("Getting current page for $community")


        GlobalScope.launch {
            lemmyApiInterface.getPosts(page = page, limit = null, auth = authRepository.getAuthToken(), community_id = community).body()?.let {

                it.posts.parallelForEach { post ->
                    if(post.thumbnail_url != null) {
                        post.internalThumbnail = Uri.parse("https://dev.lemmy.ml/pictshare/192/" + post.thumbnail_url)
                    } else if (!post.url.isNullOrEmpty()) {
                        val url = Uri.parse(post.url)
                        try {
                            // Check if the url is a image or not
                            val con: URLConnection = URL(post.url).openConnection()
                            if (con is HttpURLConnection) {
                                /* Workaround for https://code.google.com/p/android/issues/detail?id=61013 */
                                con.addRequestProperty("Accept-Encoding", "identity")
                                con.requestMethod = "HEAD"

                                if (con.responseCode != HttpURLConnection.HTTP_ACCEPTED) {
                                    var thumbUrl: Uri? = null

                                    // If the given file is an image then its what we want to load
                                    if (con.contentType.startsWith("image/")) {
                                        thumbUrl = url
                                        // If the url isnt an image then load from opengraph
                                    } else {
                                        val document: Document = Jsoup.connect(post.url).get()

                                        val meta = document.head().getElementsByTag("meta")

                                        for (tag in meta) {
                                            val hasOgImage =
                                                tag.attributes()
                                                    .find { it.key == "property" && it.value == "og:image" }
                                            if (hasOgImage != null) {
                                                val tagContent = tag.attributes()
                                                    .find { it.key == "content" }?.value
                                                thumbUrl = Uri.parse(tagContent)

                                                if(thumbUrl.isRelative) {
                                                    thumbUrl = Uri.withAppendedPath(url, tagContent)
                                                }

                                                break
                                            }
                                        }
                                    }

                                    post.internalThumbnail = thumbUrl

                                }
                            }
                            con.getInputStream().close()
                        } catch (x: java.lang.Exception) {
                            x.printStackTrace()
                            println("Unable to load thumb for post: $post")
                        }
                    }

                    println("Post has thumb: ${post.internalThumbnail} : ${post.url} : ${post.thumbnail_url}")
                }


                mutableLiveData.postValue(it.posts)
            }
        }
    }

    fun getNextPage(community: Int? = null) {
        page += 1
        getCurrentPage(community)
    }

    fun getPost(id: Int): LiveData<PostResponse> {
        val mutableLiveData = MutableLiveData<PostResponse>()


        GlobalScope.launch {
            val res = lemmyApiInterface.getPost(id, authRepository.getAuthToken())
            println("Got single post($id): $res")
            if(res.isSuccessful) {
                mutableLiveData.postValue(res.body()!!)
            }
        }

        return mutableLiveData
    }

    fun votePost(post_id: Int, vote: PostVote) {
        GlobalScope.launch {
            val res = lemmyApiInterface.likePost(PostLike(post_id, vote.score, authRepository.getAuthToken()!!))
            println("Submitted like($post_id, $vote) = $res")
            if(res.isSuccessful) {
                mutableLiveData.postValue(listOf(res.body()!!.post))
            }
        }
    }

    fun savePost(post_id: Int, save: Boolean) {
        GlobalScope.launch {
            val res = lemmyApiInterface.savePost(PostSave(post_id, save, authRepository.getAuthToken()!!))
            println("Submitted save($post_id, $save) = $res")
            if(res.isSuccessful) {
                mutableLiveData.postValue(listOf(res.body()!!.post))
            }
        }
    }
}

enum class PostVote(val score: Int) {
    UPVOTE(1),
    NEUTRAL(0),
    DOWNVOTE(-1)
}

suspend inline fun <T, R> Collection<T>.parallelForEach(crossinline func: (T) -> R) {
    val coroutimes = mutableListOf<Deferred<R>>()
    this.forEach {
        val c: Deferred<R> = GlobalScope.async {
            func(it)
        }
        coroutimes.add(c)
    }
    coroutimes.awaitAll()
}