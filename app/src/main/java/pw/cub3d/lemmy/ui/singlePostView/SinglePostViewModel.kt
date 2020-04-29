package pw.cub3d.lemmy.ui.singlePostView

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pw.cub3d.lemmy.core.data.CommentVote
import pw.cub3d.lemmy.core.data.CommentsRepository
import pw.cub3d.lemmy.core.data.PostsRepository
import javax.inject.Inject

class SinglePostViewModel @Inject constructor(
    private val postsRepository: PostsRepository,
    private val commentsRepository: CommentsRepository
): ViewModel() {
    var postId: Int = 0
    fun getPost() = postsRepository.getPost(postId)
    fun unvoteComment(id: Int) {
        GlobalScope.launch {
            commentsRepository.setVote(postId, id, CommentVote.NEUTRAL)
        }
    }

    fun upvoteComment(id: Int) {
        GlobalScope.launch {
            commentsRepository.setVote(postId, id, CommentVote.UPVOTE)
        }
    }

    fun downvoteComment(id: Int) {
        GlobalScope.launch {
            commentsRepository.setVote(postId, id, CommentVote.DOWNVOTE)
        }
    }

    fun unSave(id: Int) {
        GlobalScope.launch {
            commentsRepository.saveComment(id,  false)
        }
    }

    fun saveComment(id: Int) {
        GlobalScope.launch {
            commentsRepository.saveComment(id,  true)
        }
    }
}
