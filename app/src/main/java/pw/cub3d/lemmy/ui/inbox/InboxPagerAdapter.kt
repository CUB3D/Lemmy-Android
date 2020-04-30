package pw.cub3d.lemmy.ui.inbox

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import pw.cub3d.lemmy.ui.postListView.PostViewFragment

class InboxPagerAdapter(
    fm: FragmentManager,
    lc: Lifecycle
): FragmentStateAdapter(fm, lc) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int) = when (position) {
        0 -> PostViewFragment()
        1 -> PostViewFragment()
        2 -> PostViewFragment()
        else -> TODO("Not possible")
    }

}
