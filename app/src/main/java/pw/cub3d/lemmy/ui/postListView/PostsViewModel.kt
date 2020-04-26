package pw.cub3d.lemmy.ui.postListView

import androidx.lifecycle.ViewModel
import pw.cub3d.lemmy.core.data.PostVote
import pw.cub3d.lemmy.core.data.PostsRepository
import pw.cub3d.lemmy.core.networking.PostView
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    private val postsRepository: PostsRepository
): ViewModel() {
    fun getNextPage() = postsRepository.getNextPage()

    fun onDownvote(post: PostView) {
        postsRepository.votePost(post.id, PostVote.DOWNVOTE)
    }

    fun onUpvote(post: PostView) {
        postsRepository.votePost(post.id, PostVote.UPVOTE)
    }

    fun onUnvote(post: PostView) {
        postsRepository.votePost(post.id, PostVote.UPVOTE)
    }

    val posts = postsRepository.getPosts()
}
