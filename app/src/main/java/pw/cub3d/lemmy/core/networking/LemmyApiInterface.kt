package pw.cub3d.lemmy.core.networking

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

    @GET("modlog")
    suspend fun getModLog(
        @Query("mod_user_id") mod_user_id: Int?,
        @Query("community_id") community_id: Int?,
        @Query("page") page: Long?,
        @Query("limit") limit: Long?
    ): Response<GetModlogResult>
}

@JsonClass(generateAdapter = true)
data class GetModlogResult(
   val removed_posts: Array<ModRemovePostView>
//   val locked_posts: Array<ModLockPostView>,
//   val removed_comments: Array<ModRemoveCommentView>,
//   val removed_communities: Array<ModRemoveCommunityView>,
//   val banned_from_community: Array<ModBanFromCommunityView>,
//   val banned: Array<ModBanView>,
//   val added_to_community: Array<ModAddCommunityView>,
//   val added: Array<ModAddView>,
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
//Get Modlog
//Request
//Response
//HTTP
//Create Site
//Request
//Response
//HTTP
//Edit Site
//Request
//Response
//HTTP
//Get Site
//Request
//Response
//HTTP
//Transfer Site
//Request
//Response
//HTTP
//Get Site Config
//Request
//Response
//HTTP
//Save Site Config
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
//Edit Community
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
