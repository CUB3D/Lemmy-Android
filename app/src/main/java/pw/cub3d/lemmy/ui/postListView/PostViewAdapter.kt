package pw.cub3d.lemmy.ui.postListView

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.core.networking.PostView
import pw.cub3d.lemmy.databinding.PostEntryBinding


class PostViewAdapter(
    private val ctx: Activity,
    private val navController: NavController
) : RecyclerView.Adapter<PostViewHolder>() {
    private val layoutInflater = LayoutInflater.from(ctx)
    var posts = mutableListOf<PostView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(
        DataBindingUtil.inflate(layoutInflater, R.layout.post_entry, parent, false),
        navController
    )

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    fun updateData(newPosts: List<PostView>) {
        this.posts.addAll(newPosts)
        this.posts = this.posts.distinctBy { it.id }.toMutableList()
        notifyDataSetChanged()
    }
}

class PostViewHolder(
    val view: PostEntryBinding,
    private val navController: NavController
): RecyclerView.ViewHolder(view.root) {
    fun bind(post: PostView) {
        view.postView = post

        view.postEntryImage.setImageDrawable(null)
        view.postEntryImage.visibility =
            if (post.internalThumbnail != null) View.VISIBLE else View.GONE

        if (post.internalThumbnail != null) {
            println("Loading thumb: ${post.internalThumbnail}")
            Glide.with(view.root)
                .load(post.internalThumbnail)
                .into(view.postEntryImage)
        }

        view.root.setOnClickListener {
            navController.navigate(PostViewFragmentDirections.actionPostListViewFragmentToSinglePostFragment(post.id))
        }
    }
}
