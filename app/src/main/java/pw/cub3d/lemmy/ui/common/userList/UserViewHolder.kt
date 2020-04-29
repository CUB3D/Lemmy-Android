package pw.cub3d.lemmy.ui.common.userList

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.core.networking.UserView
import pw.cub3d.lemmy.core.utility.GlideApp
import pw.cub3d.lemmy.databinding.UserEntryBinding

class UserViewHolder(val view: UserEntryBinding): RecyclerView.ViewHolder(view.root) {
    fun bind(user: UserView) {
        view.userView = user

        if(user.avatar != null) {
            GlideApp.with(view.root)
                .load(Uri.parse(user.avatar))
                .into(view.userEntryAvatar)
        } else {
            GlideApp.with(view.root)
                .load(R.drawable.ic_star)
                .into(view.userEntryAvatar)
        }
    }
}