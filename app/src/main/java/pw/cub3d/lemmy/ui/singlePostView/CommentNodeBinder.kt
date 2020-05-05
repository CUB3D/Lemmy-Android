package pw.cub3d.lemmy.ui.singlePostView

import android.graphics.Color
import android.net.Uri
import android.view.View
import io.noties.markwon.Markwon
import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.core.data.CommentVote
import pw.cub3d.lemmy.core.networking.comment.CommentView
import pw.cub3d.lemmy.core.utility.EmojiFormat
import pw.cub3d.lemmy.core.utility.GlideApp
import pw.cub3d.lemmy.databinding.CommentEntryBinding
import tellh.com.recyclertreeview_lib.TreeNode
import tellh.com.recyclertreeview_lib.TreeViewBinder

class CommentNodeBinder(
    private val singlePostViewModel: SinglePostViewModel
): TreeViewBinder<CommentViewHolder>() {
    override fun bindView(viewHolder: CommentViewHolder, p1: Int, p2: TreeNode<*>) {
        val comment = p2.content as CommentItem
        viewHolder.bind(comment.comment)
    }

    override fun getLayoutId() = R.layout.comment_entry

    override fun provideViewHolder(itemView: View): CommentViewHolder {
        return CommentViewHolder(CommentEntryBinding.bind(itemView), singlePostViewModel)
    }

}

class CommentViewHolder(
    val binding: CommentEntryBinding,
    val singlePostViewModel: SinglePostViewModel
): TreeViewBinder.ViewHolder(binding.root) {


    fun bind(content: CommentView) {
        binding.commentView = content

        binding.commentEntryActions.visibility = View.GONE

        if(content.creator_avatar != null) {
            GlideApp.with(binding.root.context)
                .load(Uri.parse(content.creator_avatar))
                .into(binding.commentEntryAuthorImage)
            binding.commentEntryAuthorImage.visibility = View.VISIBLE
        } else {
            binding.commentEntryAuthorImage.visibility = View.GONE
        }


        binding.root.setOnClickListener {
            binding.commentEntryActions.visibility = if(binding.commentEntryActions.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        val emoji = EmojiFormat.formatText(binding.view.context, content.content)

//        val emoji = EmojiParser.parseToUnicode(content.content)
        Markwon.create(binding.view.context).setMarkdown(binding.commentEntryContent, emoji)

        if(content.my_vote == CommentVote.UPVOTE.score.toInt()) {
            binding.commentEntryUpvote.setBackgroundColor(Color.RED)
            binding.commentEntryUpvote.setOnClickListener {
                singlePostViewModel.unvoteComment(content.id)
            }
        } else {
            binding.commentEntryUpvote.setBackgroundColor(Color.TRANSPARENT)
            binding.commentEntryUpvote.setOnClickListener {
                singlePostViewModel.upvoteComment(content.id)
            }
        }

        if(content.my_vote == CommentVote.DOWNVOTE.score.toInt()) {
            binding.commentEntryDownvote.setBackgroundColor(Color.RED)
            binding.commentEntryDownvote.setOnClickListener {
                singlePostViewModel.unvoteComment(content.id)
            }
        } else {
            binding.commentEntryDownvote.setBackgroundColor(Color.TRANSPARENT)
            binding.commentEntryDownvote.setOnClickListener {
                singlePostViewModel.downvoteComment(content.id)
            }
        }

        if(content.saved == true) {
            binding.commentEntrySave.setBackgroundColor(Color.RED)
            binding.commentEntrySave.setOnClickListener {
                singlePostViewModel.unSave(content.id)
            }
        } else {
            binding.commentEntrySave.setBackgroundColor(Color.TRANSPARENT)
            binding.commentEntrySave.setOnClickListener {
                singlePostViewModel.saveComment(content.id)
            }
        }
    }
}