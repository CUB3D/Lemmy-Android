package pw.cub3d.lemmy.ui.postView

import android.app.Activity
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.core.networking.PostView
import pw.cub3d.lemmy.databinding.PostEntryBinding
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection


class PostViewAdapter(
    private val ctx: Activity
) : RecyclerView.Adapter<PostViewHolder>() {
    private val layoutInflater = LayoutInflater.from(ctx)
    val posts = mutableListOf<PostView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(
        DataBindingUtil.inflate(layoutInflater, R.layout.post_entry, parent, false)
    )

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])

//        //TODO: this needs moved to the repository
//        if (!posts[position].url.isNullOrEmpty()) {
//            GlobalScope.launch {
//
//                val url = Uri.parse(posts[position].url)
//                try {
//                    // Check if the url is a image or not
//                    val con: URLConnection = URL(posts[position].url).openConnection()
//                    if (con is HttpURLConnection) {
//                        /* Workaround for https://code.google.com/p/android/issues/detail?id=61013 */
//                        con.addRequestProperty("Accept-Encoding", "identity")
//                        con.requestMethod = "HEAD"
//
//                        if (con.responseCode != HttpURLConnection.HTTP_ACCEPTED) {
//                            var thumbUrl: Uri? = null
//
//                            // If the given file is an image then its what we want to load
//                            if (con.contentType.startsWith("image/")) {
//                                thumbUrl = url
//                                // If the url isnt an image then load from opengraph
//                            } else {
//                                val document: Document = Jsoup.connect(posts[position].url).get()
//
//                                val meta = document.head().getElementsByTag("meta")
//
//                                for (tag in meta) {
//                                    val hasOgImage =
//                                        tag.attributes()
//                                            .find { it.key == "property" && it.value == "og:image" }
//                                    if (hasOgImage != null) {
//                                        thumbUrl = Uri.parse(tag.attributes().find { it.key == "content" }?.value)
//                                        break
//                                    }
//                                }
//                            }
//
//                            if (thumbUrl != null) {
//                                ctx.runOnUiThread {
//                                    Glide.with(holder.view.root)
//                                        .load(thumbUrl)
//                                        .into(holder.view.postEntryImage)
//                                }
//                            }
//
//                        }
//                    }
//                    con.getInputStream().close()
//                } catch (x: java.lang.Exception) {
//                    x.printStackTrace()
//                    println("Unable to load post: ${posts[position]}")
//                }
//            }
//        }
    }

    fun updateData(posts: List<PostView>) {
        this.posts.addAll(posts)
        notifyDataSetChanged()
    }
}

class PostViewHolder(val view: PostEntryBinding): RecyclerView.ViewHolder(view.root) {
    fun bind(post: PostView) {
        view.postView = post

        view.postEntryImage.setImageDrawable(null)
        view.postEntryImage.visibility = if (post.internalThumbnail != null) View.VISIBLE else View.GONE

        if(post.internalThumbnail != null) {
            println("Loading thumb: ${post.internalThumbnail}")
            Glide.with(view.root)
                .load(post.internalThumbnail)
                .into(view.postEntryImage)
        }
    }
}
