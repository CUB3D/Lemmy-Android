package pw.cub3d.lemmy.ui.postListView

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.core.data.PostVote
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
        DataBindingUtil.inflate(layoutInflater, R.layout.post_entry, parent, false),
        navController,
        viewModel
    )

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
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
}

class PostViewHolder(
    val view: PostEntryBinding,
    private val navController: NavController,
    private val viewModel: PostsViewModel
): RecyclerView.ViewHolder(view.root) {
    fun bind(post: PostView) {
        view.postView = post

        view.postEntryImage.setImageDrawable(null)

        if (post.internalThumbnail != null) {
            println("Loading thumb: ${post.internalThumbnail}")
            Glide.with(view.root)
                .load(post.internalThumbnail)
                .into(view.postEntryImage)
        } else {
            Glide.with(view.root)
                .load(R.drawable.ic_message_square)
                .into(view.postEntryImage)
        }

        view.root.setOnClickListener {
            navController.navigate(PostViewFragmentDirections.actionPostListViewFragmentToSinglePostFragment(post.id))
        }

        if(post.my_vote == PostVote.UPVOTE.score) {
            view.postEntryUpvote.setOnClickListener {
                viewModel.onUnvote(post)
            }
            view.postEntryUpvote.setBackgroundColor(Color.RED)
        } else {
            view.postEntryUpvote.setBackgroundColor(Color.TRANSPARENT)
            view.postEntryUpvote.setOnClickListener {
                viewModel.onUpvote(post)
            }
        }

        if(post.my_vote == PostVote.DOWNVOTE.score) {
            view.postEntryDownvote.setOnClickListener {
                viewModel.onUnvote(post)
            }
            view.postEntryDownvote.setBackgroundColor(Color.RED)
        } else {
            view.postEntryDownvote.setBackgroundColor(Color.TRANSPARENT)
            view.postEntryDownvote.setOnClickListener {
                viewModel.onDownvote(post)
            }
        }
    }
}
