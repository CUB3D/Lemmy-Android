package pw.cub3d.lemmy.core.data

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import pw.cub3d.lemmy.core.networking.*
import pw.cub3d.lemmy.core.networking.community.Community
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import javax.inject.Inject

class PostsRepository @Inject constructor(
    private val lemmyApiInterface: LemmyApiInterface,
    private val authRepository: AuthRepository
) {

    private val postCache = mutableMapOf<Int, PostView>()

    suspend fun loadPostThumbnail(post: PostView) = GlobalScope.launch(Dispatchers.IO) {
        if (post.thumbnail_url != null) {
            post.internalThumbnail =
                Uri.parse("https://dev.lemmy.ml/pictshare/192/" + post.thumbnail_url)
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

                                    if (thumbUrl.isRelative) {
                                        thumbUrl =
                                            Uri.withAppendedPath(url, tagContent)
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
    }

    fun getCurrentPage(
        community: MutableLiveData<Int?>,
        type: MutableLiveData<GetPostType>,
        currentPage: LiveData<Int>,
        sortType: LiveData<SortType>
    ): Flow<PostView> = flow {
        println("Getting current page for $community")

        community.asFlow().collect { community ->
            type.asFlow().collect { type ->
                currentPage.asFlow().collect { page ->
                    sortType.asFlow().collect { sortType ->
                        lemmyApiInterface.getPosts(
                            page = page,
                            limit = null,
                            auth = authRepository.getAuthToken(),
                            community_id = community,
                            type_ = type.id,
                            sort = sortType.id
                        ).body()?.let {

                            it.posts.sortedByDescending { it.hot_rank }.forEach {
                                postCache[it.id] = it
                                emit(it)
                            }
                        }
                    }
                }
            }
        }
    }.map { post ->
        loadPostThumbnail(post)
        post
    }

    fun getPost(id: Int): LiveData<PostResponse> {
        val mutableLiveData = MutableLiveData<PostResponse>()

        if(postCache.containsKey(id)) {
            mutableLiveData.postValue(PostResponse(postCache[id]!!, emptyArray(), Community(-1, "", "", "", 0, 0, "", null, "", 0, 0, 0, 0, null, false)))
        }

        GlobalScope.launch {
            val res = lemmyApiInterface.getPost(id, authRepository.getAuthToken())
            println("Got single post($id): $res")
            if(res.isSuccessful) {
                mutableLiveData.postValue(res.body()!!)
            }
        }

        return mutableLiveData
    }

    suspend fun votePost(post_id: Int, vote: PostVote): PostView? {
            val res = lemmyApiInterface.likePost(PostLike(post_id, vote.score, authRepository.getAuthToken()!!))
            println("Submitted like($post_id, $vote) = $res")
            if(res.isSuccessful) {
                return res.body()!!.post
               // mutableLiveData.postValue(listOf(res.body()!!.post))
            } else {
                return null
            }
    }

    suspend fun savePost(post_id: Int, save: Boolean): PostView? {
        val res =
            lemmyApiInterface.savePost(PostSave(post_id, save, authRepository.getAuthToken()!!))
        println("Submitted save($post_id, $save) = $res")
        if (res.isSuccessful) {
            return res.body()!!.post
        } else {
            return null
        }
    }
}

enum class GetPostType(val id: String) {
    ALL("All"),
    SUBSCRIBED("Subscribed"),
    COMMUNITY("Community")
}