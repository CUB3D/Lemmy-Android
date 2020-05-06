package pw.cub3d.lemmy.ui.postListView

import android.app.Activity
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.core.data.PostVote
import pw.cub3d.lemmy.core.networking.PostView
import pw.cub3d.lemmy.core.utility.GlideApp
import pw.cub3d.lemmy.databinding.PostEntryBinding
import pw.cub3d.lemmy.ui.home.HomeFragmentDirections
import pw.cub3d.lemmy.ui.imageDetail.ImageDetailFragment
import pw.cub3d.lemmy.ui.postListView.PostsViewModel

class PostViewHolder(
    val view: PostEntryBinding,
    private val activity: FragmentActivity,
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
                .apply(RequestOptions.circleCropTransform())
                .into(view.postEntrySenderAvatar)
        } else {
            view.postEntrySenderAvatar.visibility = View.GONE
        }

        if (post.internalThumbnail != null) {
            view.postEntryImage.transitionName = post.internalThumbnail.toString()
            view.postEntryImage.setOnClickListener {
                val a = HomeFragmentDirections.actionHomeFragmentToImageDetailFragment(post.internalThumbnail!!.toString(), post.id)

                navController.navigate(
                    a,
                    FragmentNavigatorExtras(
                        view.postEntryImage to post.internalThumbnail.toString()
                    )
                )
            }

            println("Loading thumb: ${post.internalThumbnail}")
            GlideApp.with(view.root)
                .load(post.internalThumbnail)
                .into(view.postEntryImage)
        } else {
            GlideApp.with(view.root)
                .load(R.drawable.ic_message_square)
                .into(view.postEntryImage)

            view.postEntryImage.setOnClickListener {}
        }

        view.postEntryTitle.transitionName = "${post.id}_title"
        view.root.setOnClickListener {
            navController.navigate(
                R.id.singlePostFragment,
                Bundle().apply {
                    putInt("postId", post.id)
                },
                null,
                FragmentNavigatorExtras(
                    view.postEntryTitle to "${post.id}_title"
                )
            )
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