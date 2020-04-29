package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import pw.cub3d.lemmy.core.networking.community.CommunitiesListResponse
import pw.cub3d.lemmy.core.networking.login.LoginRequest
import pw.cub3d.lemmy.core.networking.login.LoginResponse
import pw.cub3d.lemmy.core.networking.register.RegisterRequest
import pw.cub3d.lemmy.core.networking.user.UserDetailsResponse
import retrofit2.Response
import retrofit2.http.*

interface LemmyApiInterface {
    @POST("user/register")
    suspend fun register(@Body request: RegisterRequest): Response<LoginResponse>

    @POST("user/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("user")
    suspend fun getUserDetails(
        @Query("user_id") userId: Int? = null,
        @Query("username") username: String? = null,
        @Query("sort") sort: String,
        @Query("page") page: Long? = null,
        @Query("limit") limit: Long? = null,
        @Query("community_id") community_id: Int? = null,
        @Query("saved_only") savedOnly: Boolean,
        @Query("auth") auth: String? = null
    ): Response<UserDetailsResponse>

    @PUT("user/save_user_settings")
    suspend fun saveUserSettings(@Body settings: SaveUserSettingsRequest): Response<SaveUserSettingsResponse>


    @GET("community/list")
    suspend fun listCommunities(@Query("sort") sort: String = "Hot"): Response<CommunitiesListResponse>

    @GET("post/list")
    suspend fun getPosts(
        @Query("type_") type_: String = "All",
        @Query("sort") sort: String = "Hot",
        @Query("page") page: Int? = 1,
        @Query("limit") limit: Int? = null,
        @Query("community_id") community_id: Int? = null,
        @Query("auth") auth: String? = null
    ): Response<PostListResponse>

    @GET("post")
    suspend fun getPost(
        @Query("id") id: Int,
        @Query("auth") auth: String?
    ): Response<PostResponse>

    @POST("post/like")
    suspend fun likePost(@Body data: PostLike): Response<PostLikeResponse>

    @PUT("post/save")
    suspend fun savePost(@Body data: PostSave): Response<PostSaveResponse>

    @POST("comment/like")
    suspend fun likeComment(@Body data: CommentLike): Response<CommentLikeResponse>

    @POST("comment/save")
    suspend fun saveComment(@Body data: CommentSave): Response<CommentSaveResponse>


    @GET("modlog")
    suspend fun getModLog(
        @Query("mod_user_id") mod_user_id: Int?,
        @Query("community_id") community_id: Int?,
        @Query("page") page: Long?,
        @Query("limit") limit: Long?
    ): Response<GetModlogResult>
}

@JsonClass(generateAdapter = true)
data class CommentSave(
    val comment_id: Int,
    val save: Boolean,
    val auth: String
)

@JsonClass(generateAdapter = true)
data class CommentSaveResponse(
    val comment: CommentView
)

@JsonClass(generateAdapter = true)
data class CommentLike(
    val comment_id: Int,
    val post_id: Int,
    val score: Short,
    val auth: String
)

@JsonClass(generateAdapter = true)
data class CommentLikeResponse(
    val comment: CommentView
)

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

@JsonClass(generateAdapter = true)
data class ModRemovePostView(
    val id: Int,
    val mod_user_id: Int,
    val post_id: Int,
    val reason: String?,
    val removed: Boolean,
    val when_: String,
    val mod_user_name: String,
    val post_name: String,
    val community_id: Int,
    val community_name: String
)
@JsonClass(generateAdapter = true)
data class ModLockPostView(
    val id: Int,
    val mod_user_id: Int,
    val post_id: Int,
    val locked: Boolean,
    val when_: String,
    val mod_user_name: String,
    val post_name: String,
    val community_id: Int,
    val community_name: String
)
@JsonClass(generateAdapter = true)
data class ModStickiedPostView(
    val id: Int,
    val mod_user_id: Int,
    val post_id: Int,
    val stickied: Boolean,
    val when_: String,
    val mod_user_name: String,
    val community_id: Int?,
    val community_name: String
)
@JsonClass(generateAdapter = true)
data class ModRemoveCommentView(
    val id: Int,
    val mod_user_id: Int,
    val comment_id: Int,
    val reason: String?,
    val removed: Boolean,
    val when_: String,
    val mod_user_name: String,
    val comment_user_id: Int,
    val comment_user_name: String,
    val comment_content: String,
    val post_id: Int,
    val post_name: String,
    val community_id: Int?,
    val community_name: String
)
@JsonClass(generateAdapter = true)
data class ModRemoveCommunityView(
    val id: Int,
    val mod_user_id: Int,
    val community_id: Int,
    val reason: String?,
    val removed: Boolean,
    val expires: String?,
    val when_: String,
    val mod_user_name: String,
    val community_name: String
)
@JsonClass(generateAdapter = true)
data class ModBanFromCommunityView(
    val id: Int,
    val mod_user_id: Int,
    val other_user_id: Int,
    val community_id: Int,
    val reason: String?,
    val banned: Boolean,
    val expires: String?,
    val when_: String,
    val other_user_name: String,
    val community_name: String
)
@JsonClass(generateAdapter = true)
data class ModBanView(
    val id: Int,
    val mod_user_id: Int,
    val other_user_id: Int,
    val reason: String?,
    val banned: Boolean,
    val expires: String?,
    val when_: String,
    val mod_user_name: String,
    val other_user_name: String
)
@JsonClass(generateAdapter = true)
data class ModAddCommunityView(
    val id: Int,
    val mod_user_id: Int,
    val other_user_id: Int,
    val community_id: Int,
    val removed: Boolean,
    val when_: String,
    val mod_user_name: String,
    val other_user_name: String,
    val community_name: String
)
@JsonClass(generateAdapter = true)
data class ModAddView(
    val id: Int,
    val mod_user_id: Int,
    val other_user_id: Int,
    val removed: Boolean,
    val when_: String,
    val mod_user_name: String,
    val other_user_name: String
)

@JsonClass(generateAdapter = true)
data class SaveUserSettingsRequest(
    val show_nsfw: Boolean,
    val theme: String,
    val default_sort_type: Short,
    val default_listings_type: Short,
    val lang: String,
    val avatar: String?,
    val email: String?,
    val matrix_user_id: String?,
    val new_password: String?,
    val new_password_verify: String?,
    val old_password: String?,
    val show_avatars: Boolean,
    val send_notifications_to_email: Boolean,
    val auth: String
)

@JsonClass(generateAdapter = true)
data class SaveUserSettingsResponse(
    val jwt: String
)

//Get Replies / Inbox
//    Request
//    Response
//    HTTP
//Get User Mentions
//    Request
//    Response
//    HTTP
//Edit User Mention
//    Request
//    Response
//    HTTP
//Mark All As Read
//    Request
//    Response
//    HTTP

//Site
//
//Search
//Request
//Response
//HTTP
//Get Site
//Request
//Response
//HTTP
//
//Community
//
//Get Community
//Request
//Response
//HTTP
//Follow Community
//Request
//Response
//HTTP
//Get Followed Communities
//Request
//Response
//HTTP
//
//Post
//
//Create Post
//Request
//Response
//HTTP
//Edit Post
//Request
//Response
//HTTP
//
//Comment
//
//Create Comment
//Request
//Response
//HTTP
//Edit Comment
//Request
//Response
//HTTP
//Save Comment
//Request
//Response
//HTTP
//Create Comment Like
//Request
//Response
//HTTP
