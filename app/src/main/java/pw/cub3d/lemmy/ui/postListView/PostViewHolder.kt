package pw.cub3d.lemmy.ui.postListView

import android.graphics.Color
import android.net.Uri
import android.view.View
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.core.data.PostVote
import pw.cub3d.lemmy.core.networking.PostView
import pw.cub3d.lemmy.core.utility.GlideApp
import pw.cub3d.lemmy.databinding.PostEntryBinding
import pw.cub3d.lemmy.ui.home.HomeFragmentDirections
import pw.cub3d.lemmy.ui.postListView.PostsViewModel

class PostViewHolder(
    val view: PostEntryBinding,
    private val navController: NavController,
    private val viewModel: PostsViewModel
): RecyclerView.ViewHolder(view.root) {
    fun bind(post: PostView) {
        view.postView = post

        view.postEntryImage.setImageDrawable(null)

        if(post.creator_avatar != null) {
            view.postEntrySenderAvatar.visibility = View.VISIBLE
            GlideApp.with(view.root)
                .load(Uri.parse(post.creator_avatar))
                .into(view.postEntrySenderAvatar)
        } else {
            view.postEntrySenderAvatar.visibility = View.GONE
        }

        if (post.internalThumbnail != null) {
            println("Loading thumb: ${post.internalThumbnail}")
            GlideApp.with(view.root)
                .load(post.internalThumbnail)
                .into(view.postEntryImage)
        } else {
            GlideApp.with(view.root)
                .load(R.drawable.ic_message_square)
                .into(view.postEntryImage)
        }

        view.root.setOnClickListener {
            navController.navigate(HomeFragmentDirections.actionHomeFragmentToSinglePostFragment(post.id))
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

        if(post.saved == true) {
            view.postEntrySave.setOnClickListener {
                viewModel.unSave(post)
            }
            view.postEntrySave.setBackgroundColor(Color.RED)
        } else {
            view.postEntrySave.setOnClickListener {
                viewModel.save(post)
            }
            view.postEntrySave.setBackgroundColor(Color.TRANSPARENT)
        }

        view.root.setOnLongClickListener {
            viewModel.onPostLongPress(post)
            true
        }
    }
}