package pw.cub3d.lemmy.ui.singlePostView

import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.core.networking.comment.CommentView
import tellh.com.recyclertreeview_lib.LayoutItemType

class CommentItem(val comment: CommentView): LayoutItemType {
    override fun getLayoutId() = R.layout.comment_entry
}