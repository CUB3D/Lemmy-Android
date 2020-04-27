package pw.cub3d.lemmy.ui.postListView

import android.app.Activity
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import pw.cub3d.lemmy.core.networking.PostView
import pw.cub3d.lemmy.databinding.PostEntryBinding


class PostViewAdapter(
    private val ctx: Activity,
    private val navController: NavController,
    private val viewModel: PostsViewModel
) : RecyclerView.Adapter<PostViewHolder>() {
    private val layoutInflater = LayoutInflater.from(ctx)
    var posts = mutableListOf<PostView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(
        PostEntryBinding.inflate(layoutInflater, parent, false),
        navController,
        viewModel
    )

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    fun updateData(data: PostView) {
        val existing = posts.indexOfFirst { it.id == data.id }.takeIf { it != -1 }

        if(existing != null) {
            posts[existing] = data
            notifyItemChanged(existing)
        } else {
            posts.add(data)
            notifyItemChanged(posts.lastIndex)
        }
    }

    fun updateData(data: List<PostView>) {
        val newPostMap = data.map { it.id to it }.toMap()
        val oldPostKeys = this.posts.map { it.id }
        val newPosts = data.filterNot { oldPostKeys.contains(it.id) }

        val replacedPosts = this.posts.map {
            newPostMap[it.id] ?: it
        }.toMutableList().apply {
            addAll(newPosts)
        }

        this.posts = replacedPosts

        notifyDataSetChanged()
    }

    fun clearData() {
        this.posts.clear()
        notifyDataSetChanged()
    }
}
