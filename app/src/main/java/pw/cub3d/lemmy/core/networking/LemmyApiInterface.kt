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

    @GET("site")
    suspend fun getSite(): Response<GetSiteResponse>

    @GET("community")
    suspend fun getCommunity(
        @Query("id") id: Int?,
        @Query("name") name: String?,
        @Query("auth") auth: String?
    ): Response<GetCommunityResponse>

    @GET("user/replies")
    suspend fun getReplies(
        @Query("sort") sort: String,
        @Query("page") page: Long?,
        @Query("limit") limit: Long?,
        @Query("unread_only") unread_only: Boolean,
        @Query("auth") auth: String
    ): Response<GetRepliesResponse>

    @GET("user/mention")
    suspend fun getMentions(
        @Query("sort") sort: String,
        @Query("page") page: Long?,
        @Query("limit") limit: Long?,
        @Query("unread_only") unread_only: Boolean,
        @Query("auth") auth: String
    ): Response<GetMentionsResponse>

    @PUT("user/mention")
    suspend fun editUserMention(@Body req: UserMentionRequest): Response<UserMentionResponse>
}

@JsonClass(generateAdapter = true)
data class UserMentionResponse(
    val mention: UserMentionView
)

@JsonClass(generateAdapter = true)
data class UserMentionView(
    val id: Int,
    val user_mention_id: Int,
    val creator_id: Int,
    val post_id: Int,
    val parent_id: Int?,
    val content: String,
    val removed: Boolean,
    val read: Boolean,
    val published: String,
    val updated: String?,
    val deleted: Boolean,
    val community_id: Int,
    val community_name: String,
    val banned: Boolean,
    val banned_from_community: Boolean,
    val creator_name: String,
    val score: Int,
    val upvotes: Int,
    val downvotes: Int,
    val hot_rank: Int,
    val user_id: Int,
    val my_vote: Int,
    val saved: Boolean?,
    val recipient_id: Int
)

@JsonClass(generateAdapter = true)
data class UserMentionRequest(
    val user_mention_id: Int,
    val read: Boolean?,
    val auth: String
)

@JsonClass(generateAdapter = true)
data class GetMentionsResponse(
    val mentions: Array<UserMentionView>
)

@JsonClass(generateAdapter = true)
data class GetRepliesResponse(
    val replies: Array<ReplyView>
)

@JsonClass(generateAdapter = true)
data class ReplyView(
    val id: Int,
    val creator_id: Int,
    val parent_id: Int?,
    val content: String,
    val removed: Boolean,
    val read: Boolean,
    val published: String,
    val updated: String?,
    val deleted: Boolean,
    val community_id: Int,
    val community_name: String,
    val banned: Boolean,
    val banned_from_community: Boolean,
    val creator_name: String,
    val creator_avatar: String,
    val score: Int,
    val upvotes: Int,
    val downvotes: Int,
    val hot_rank: Int,
    val user_id: Int,
    val my_vote: Int,
    val subscribed: Boolean,
    val saved: Boolean?,
    val recipient_id: Int
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
//
//Community
//
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