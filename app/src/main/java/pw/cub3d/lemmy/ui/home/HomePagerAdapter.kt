package pw.cub3d.lemmy.ui.home

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import pw.cub3d.lemmy.core.networking.community.CommunityFollowerView
import pw.cub3d.lemmy.ui.postListView.PostViewFragment

class HomePagerAdapter(
    private val ctx: Context,
    private val fm: FragmentManager,
    private val lc: Lifecycle
): FragmentStateAdapter(fm, lc) {
    var communities = emptyArray<CommunityFollowerView>()

    override fun getItemCount() = communities.size

    override fun createFragment(position: Int): Fragment {
        // 0 Is your feed
        return if(position == 0) {
            PostViewFragment()
        } else {
            PostViewFragment().apply {
                community = communities[position].community_id
            }
        }
    }

    fun updateData(data: Array<CommunityFollowerView>) {
        communities = arrayOf(CommunityFollowerView(-1, -1, -1, "", "", "", "Feed")) + data
        notifyDataSetChanged()
    }

}