package pw.cub3d.lemmy.ui.singlePostView

import android.graphics.Color
import android.view.View
import com.vdurmont.emoji.EmojiParser
import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.core.data.CommentVote
import pw.cub3d.lemmy.core.data.PostVote
import pw.cub3d.lemmy.databinding.CommentEntryBinding
import tellh.com.recyclertreeview_lib.TreeNode
import tellh.com.recyclertreeview_lib.TreeViewBinder

class CommentNodeBinder(
    private val singlePostViewModel: SinglePostViewModel
): TreeViewBinder<CommentViewHolder>() {
    override fun bindView(viewHolder: CommentViewHolder, p1: Int, p2: TreeNode<*>) {
        val comment = p2.content as CommentItem
        viewHolder.bind(comment)
    }

    override fun getLayoutId() = R.layout.comment_entry

    override fun provideViewHolder(itemView: View): CommentViewHolder {
        return CommentViewHolder(itemView, singlePostViewModel)
    }

}

class CommentViewHolder(
    root: View,
    val singlePostViewModel: SinglePostViewModel
): TreeViewBinder.ViewHolder(root) {

    private val binding = CommentEntryBinding.bind(root)

    fun bind(content: CommentItem) {
        binding.commentEntryActions.visibility = View.GONE

        binding.root.setOnClickListener {
            binding.commentEntryActions.visibility = if(binding.commentEntryActions.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        val emoji = EmojiParser.parseToUnicode(content.comment.content)
        binding.commentEntryContent.loadMarkdown(emoji, "file://android_asset/Comments.css")

        if(content.comment.my_vote == CommentVote.UPVOTE.score.toInt()) {
            binding.commentEntryUpvote.setBackgroundColor(Color.RED)
            binding.commentEntryUpvote.setOnClickListener {
                singlePostViewModel.unvoteComment(content.comment.id)
            }
        } else {
            binding.commentEntryUpvote.setBackgroundColor(Color.TRANSPARENT)
            binding.commentEntryUpvote.setOnClickListener {
                singlePostViewModel.upvoteComment(content.comment.id)
            }
        }

        if(content.comment.my_vote == CommentVote.DOWNVOTE.score.toInt()) {
            binding.commentEntryDownvote.setBackgroundColor(Color.RED)
            binding.commentEntryDownvote.setOnClickListener {
                singlePostViewModel.unvoteComment(content.comment.id)
            }
        } else {
            binding.commentEntryDownvote.setBackgroundColor(Color.TRANSPARENT)
            binding.commentEntryDownvote.setOnClickListener {
                singlePostViewModel.downvoteComment(content.comment.id)
            }
        }

        if(content.comment.saved == true) {
            binding.commentEntrySave.setBackgroundColor(Color.RED)
            binding.commentEntrySave.setOnClickListener {
                singlePostViewModel.unSave(content.comment.id)
            }
        } else {
            binding.commentEntrySave.setBackgroundColor(Color.TRANSPARENT)
            binding.commentEntrySave.setOnClickListener {
                singlePostViewModel.saveComment(content.comment.id)
            }
        }
    }
}