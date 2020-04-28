package pw.cub3d.lemmy.ui.profile

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import pw.cub3d.lemmy.core.networking.PostView
import pw.cub3d.lemmy.ui.postListView.PostViewFragment

class ProfilePagerAdapter(
    private val ctx: Context,
    private val fm: FragmentManager,
    private val lc: Lifecycle,
    private val posts: Array<PostView>
): FragmentStateAdapter(fm, lc) {

    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            // Overview
            0 -> PostViewFragment()
            // Comments
            1 -> PostViewFragment()
            // Posts
            2 -> PostViewFragment().apply {
                posts = this@ProfilePagerAdapter.posts
            }
            // Saved
            3 -> PostViewFragment()
            else -> TODO("Never will occur")
        }
    }
}