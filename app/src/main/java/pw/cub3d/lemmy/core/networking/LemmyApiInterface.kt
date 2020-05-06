package pw.cub3d.lemmy.core.networking

import pw.cub3d.lemmy.core.networking.comment.CommentLike
import pw.cub3d.lemmy.core.networking.comment.CommentLikeResponse
import pw.cub3d.lemmy.core.networking.comment.CommentSave
import pw.cub3d.lemmy.core.networking.comment.CommentSaveResponse
import pw.cub3d.lemmy.core.networking.community.CommunitiesListResponse
import pw.cub3d.lemmy.core.networking.community.CommunityFollowRequest
import pw.cub3d.lemmy.core.networking.community.CommunityFollowResponse
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

    @POST("community/follow")
    suspend fun followCommunity(@Body request: CommunityFollowRequest): Response<CommunityFollowResponse>


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

    @POST("post")
    suspend fun createPost(@Body data: PostCreateRequest): Response<PostCreateResponse>

    @POST("comment/like")
    suspend fun likeComment(@Body data: CommentLike): Response<CommentLikeResponse>

    @POST("comment/save")
    suspend fun saveComment(@Body data: CommentSave): Response<CommentSaveResponse>

    @POST("comment")
    suspend fun createComment(@Body data: CreateCommentRequest): Response<CreateCommentResponse>

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
    suspend fun editUserMention(@Body req: EditUserMentionRequest): Response<UserMentionResponse>

    @POST("user/mark_all_as_read")
    suspend fun markAllAsRead(@Body req: MarkAllAsReadRequest): Response<MarkAllAsReadResponse>

    @GET("search")
    suspend fun search(
        @Query("q") q: String,
        @Query("type_") type_: String,
        @Query("community_id") community_id: Int?,
        @Query("sort") sort: String,
        @Query("page") page: Long?,
        @Query("limit") limit: Long?,
        @Query("auth") auth: String?
    ): Response<SearchResponse>
}

