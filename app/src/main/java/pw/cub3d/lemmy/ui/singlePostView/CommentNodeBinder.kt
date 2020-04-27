package pw.cub3d.lemmy.ui.singlePostView

import android.view.View
import com.vdurmont.emoji.EmojiParser
import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.databinding.CommentEntryBinding
import tellh.com.recyclertreeview_lib.TreeNode
import tellh.com.recyclertreeview_lib.TreeViewBinder

class CommentNodeBinder: TreeViewBinder<CommentViewHolder>() {
    override fun bindView(viewHolder: CommentViewHolder, p1: Int, p2: TreeNode<*>) {
        val comment = p2.content as CommentItem
        viewHolder.bind(comment)
    }

    override fun getLayoutId() = R.layout.comment_entry

    override fun provideViewHolder(itemView: View): CommentViewHolder {
        return CommentViewHolder(itemView)
    }

}

class CommentViewHolder(root: View): TreeViewBinder.ViewHolder(root) {

    private val binding = CommentEntryBinding.bind(root)

    fun bind(content: CommentItem) {
        val emoji = EmojiParser.parseToUnicode(content.comment.content)
        println("emoji: $emoji")
        binding.commentEntryContent.loadMarkdown(emoji, "file://android_asset/Comments.css")
    }
}