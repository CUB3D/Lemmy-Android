package pw.cub3d.lemmy.core.data

enum class PostVote(val score: Int) {
    UPVOTE(1),
    NEUTRAL(0),
    DOWNVOTE(-1)
}