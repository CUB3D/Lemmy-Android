package pw.cub3d.lemmy.core.data

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import pw.cub3d.lemmy.core.networking.LemmyApiInterface
import pw.cub3d.lemmy.core.networking.PostResponse
import pw.cub3d.lemmy.core.networking.PostView
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsRepository @Inject constructor(
    private val lemmyApiInterface: LemmyApiInterface,
    private val authRepository: AuthRepository
) {
    private var page = 1

    private val mutableLiveData = MutableLiveData<List<PostView>>()

    fun getPosts(): LiveData<List<PostView>> {
        page = 1
        getCurrentPage()

        return mutableLiveData
    }

    fun getCurrentPage() {
        GlobalScope.launch {
            lemmyApiInterface.getPosts(page = page, limit = null, auth = authRepository.getAuthToken()).body()?.let {

                it.posts.forEach { post ->
                    if (!post.url.isNullOrEmpty()) {
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
                                                thumbUrl = Uri.parse(
                                                    tag.attributes()
                                                        .find { it.key == "content" }?.value
                                                )
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


                mutableLiveData.postValue(it.posts)
            }
        }
    }

    fun getNextPage() {
        page += 1
        getCurrentPage()
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
}