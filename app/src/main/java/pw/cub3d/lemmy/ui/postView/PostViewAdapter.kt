package pw.cub3d.lemmy.ui.postView

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.core.networking.PostView
import pw.cub3d.lemmy.databinding.PostEntryBinding
import java.lang.Exception


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
        val post = posts[position]

        holder.view.postView = post

        if (post.url != null) {
            GlobalScope.launch {
                try {
                    val document: Document = Jsoup.connect(post.url).get()

                    val meta = document.head().getElementsByTag("meta")

                    var imageUrl: String? = null
                    for (tag in meta) {
                        val hasOgImage =
                            tag.attributes().find { it.key == "property" && it.value == "og:image" }
                        if (hasOgImage != null) {
                            imageUrl = tag.attributes().find { it.key == "content" }?.value
                            break
                        }
                    }

                    if (imageUrl != null) {
                        //TODO: dont use activity to do this
                        ctx.runOnUiThread {
                            Glide.with(holder.view.root)
                                .load(Uri.parse(imageUrl))
                                .into(holder.view.postEntryImage)
                        }
                    } else {
                        //TODO: dont use activity to do this
                        ctx.runOnUiThread {
                            Glide.with(holder.view.root)
                                .load(Uri.parse(post.url))
                                .into(holder.view.postEntryImage)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    //This might be a image url directly, try loading it
                    //TODO: dont use activity to do this
                    ctx.runOnUiThread {
                        Glide.with(holder.view.root)
                            .load(Uri.parse(post.url))
                            .into(holder.view.postEntryImage)
                    }
                }
            }
        } else {
            holder.view.postEntryImage.visibility = View.GONE
        }
    }

    fun updateData(posts: List<PostView>) {
        this.posts.addAll(posts)
        notifyDataSetChanged()
    }
}

class PostViewHolder(val view: PostEntryBinding): RecyclerView.ViewHolder(view.root)
