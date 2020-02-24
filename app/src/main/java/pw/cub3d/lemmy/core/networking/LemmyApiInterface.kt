package pw.cub3d.lemmy.core.networking

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LemmyApiInterface {
    @POST("user/register")
    suspend fun register(@Body request: RegisterRequest): Response<String>

    @GET("community/list")
    suspend fun listCommunities(@Query("sort") sort: String = "Hot"): Response<CommunitiesListResponse>

    @POST("user/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("post/list")
    suspend fun getPosts(
        @Query("type_") type_: String = "All",
        @Query("sort") sort: String = "Hot",
        @Query("page") page: Int? = 1,
        @Query("limit") limit: Int? = 10,
        @Query("community_id") community_id: Int? = null,
        @Query("auth") auth: String? = null
    ): Response<PostResponse>
}