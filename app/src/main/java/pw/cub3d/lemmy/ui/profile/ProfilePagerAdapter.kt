package pw.cub3d.lemmy.ui.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import pw.cub3d.lemmy.core.networking.comment.CommentView
import pw.cub3d.lemmy.core.networking.PostView
import pw.cub3d.lemmy.ui.profile.comments.MixedPostType
import pw.cub3d.lemmy.ui.profile.comments.MixedPosts
import pw.cub3d.lemmy.ui.profile.comments.StaticMixedPostsCommentsFragment

class ProfilePagerAdapter(
    private val fm: FragmentManager,
    private val lc: Lifecycle,
    private val posts: Array<PostView>,
    private val comments: Array<CommentView>
): FragmentStateAdapter(fm, lc) {

    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            // Overview
            0 -> StaticMixedPostsCommentsFragment().apply {
                entries = comments.map { MixedPosts(MixedPostType.COMMENT, it, null) } + posts.map { MixedPosts(MixedPostType.POST, null, it) }
            }
            // Comments
            1 -> StaticMixedPostsCommentsFragment().apply {
                entries = comments.map { MixedPosts(MixedPostType.COMMENT, it, null) }
            }
            // Posts
            2 -> StaticMixedPostsCommentsFragment().apply {
                entries = posts.map { MixedPosts(MixedPostType.POST, null, it) }
            }
            // Saved
//            3 -> PostViewFragment()
            else -> TODO("Never will occur")
        }
    }
}