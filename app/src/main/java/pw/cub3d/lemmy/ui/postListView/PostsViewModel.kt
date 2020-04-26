package pw.cub3d.lemmy.ui.postListView

import androidx.lifecycle.ViewModel
import pw.cub3d.lemmy.core.data.PostVote
import pw.cub3d.lemmy.core.data.PostsRepository
import pw.cub3d.lemmy.core.networking.PostView
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    private val postsRepository: PostsRepository
): ViewModel() {

    fun getNextPage(communityId: Int?) = postsRepository.getNextPage(communityId)

    fun onDownvote(post: PostView) {
        postsRepository.votePost(post.id, PostVote.DOWNVOTE)
    }

    fun onUpvote(post: PostView) {
        postsRepository.votePost(post.id, PostVote.UPVOTE)
    }

    fun onUnvote(post: PostView) {
        postsRepository.votePost(post.id, PostVote.NEUTRAL)
    }

    fun unSave(post: PostView) {
        postsRepository.savePost(post.id, false)
    }

    fun save(post: PostView) {
        postsRepository.savePost(post.id, true)
    }

    fun getPosts(communityId: Int?) = postsRepository.getPosts(communityId)
}
