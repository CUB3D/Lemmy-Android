package pw.cub3d.lemmy.ui.common.userList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pw.cub3d.lemmy.core.networking.UserView
import pw.cub3d.lemmy.databinding.UserEntryBinding
import pw.cub3d.lemmy.ui.common.userList.UserViewHolder

class UserListAdapter(
    private val ctx: Context
) : RecyclerView.Adapter<UserViewHolder>() {
    private var entries = emptyArray<UserView>()
    private val inflater = LayoutInflater.from(ctx)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(
            UserEntryBinding.inflate(
                inflater,
                parent,
                false
            )
        )

    override fun getItemCount() = entries.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(entries[position])
    }

    fun updateData(entries: Array<UserView>) {
        this.entries = entries
        notifyDataSetChanged()
    }
}