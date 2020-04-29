package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetModlogResult(
    val removed_posts: Array<ModRemovePostView>,
    val locked_posts: Array<ModLockPostView>,
    val stickied_posts: Array<ModStickiedPostView>,
    val removed_comments: Array<ModRemoveCommentView>,
    val removed_communities: Array<ModRemoveCommunityView>,
    val banned_from_community: Array<ModBanFromCommunityView>,
    val banned: Array<ModBanView>,
    val added_to_community: Array<ModAddCommunityView>,
    val added: Array<ModAddView>
)