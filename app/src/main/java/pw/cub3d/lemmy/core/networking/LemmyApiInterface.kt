package pw.cub3d.lemmy.core.networking

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
}




//Register
//    Request
//    Response
//    HTTP
//
//Save User Settings
//    Request
//    Response
//    HTTP
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
//Delete Account
//    Request
//    Response
//    HTTP
//Add admin
//    Request
//    Response
//    HTTP
//Ban user
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
//Ban from Community
//Request
//Response
//HTTP
//Add Mod to Community
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
//Transfer Community
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
//
//RSS / Atom feeds
//    All
//    Community
//    User

