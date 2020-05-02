package pw.cub3d.lemmy.core.data

import pw.cub3d.lemmy.core.networking.comment.CommentLike
import pw.cub3d.lemmy.core.networking.comment.CommentSave
import pw.cub3d.lemmy.core.networking.comment.CommentView
import pw.cub3d.lemmy.core.networking.LemmyApiInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentsRepository @Inject constructor(
    private val api: LemmyApiInterface,
    private val authRepository: AuthRepository
) {
    suspend fun setVote(postId: Int, commentId: Int, vote: CommentVote): CommentView? {
        val r = api.likeComment(
            CommentLike(
                commentId,
                postId,
                vote.score,
                authRepository.getAuthToken()!!
            )
        )
        println("voteComment($postId:$commentId, $vote) = $r")
        if(r.isSuccessful) {
            return r.body()!!.comment
        } else {
            return null
        }
    }

    suspend fun saveComment(commentId: Int, saved: Boolean): CommentView? {
        val r = api.saveComment(
            CommentSave(
                commentId,
                saved,
                authRepository.getAuthToken()!!
            )
        )
        println("saveComment($commentId, $saved) = $r")
        if(r.isSuccessful) {
            return r.body()!!.comment
        } else {
            return null
        }
    }
}

enum class CommentVote(val score: Short) {
    UPVOTE(1),
    NEUTRAL(0),
    DOWNVOTE(-1)
}