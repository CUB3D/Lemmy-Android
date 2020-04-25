package pw.cub3d.lemmy.ui.singlePostView

import android.view.View
import pw.cub3d.lemmy.R
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
    fun bind(content: CommentItem) {
        println("Binding: $content")
    }

}