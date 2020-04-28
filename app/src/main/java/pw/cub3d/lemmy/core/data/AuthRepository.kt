package pw.cub3d.lemmy.core.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.auth0.android.jwt.JWT
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pw.cub3d.lemmy.core.networking.LemmyApiInterface
import pw.cub3d.lemmy.core.networking.login.LoginRequest
import pw.cub3d.lemmy.core.networking.register.RegisterRequest
import pw.cub3d.lemmy.core.networking.user.UserClaims
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val api: LemmyApiInterface,
    private val ctx: Context
) {
    private var jwt: String? = null
    private var liveJwt = MutableLiveData<String?>(null)

    private val prefs = ctx.getSharedPreferences("LemmyAndroid", Context.MODE_PRIVATE)

    init {
        this.jwt = prefs.getString("JWT", null)
        liveJwt.postValue(jwt)
    }

    fun login(username: String, password: String) {
        GlobalScope.launch {
            val res = api.login(
                LoginRequest(
                    username,
                    password
                )
            )

            println("Got login response: $res")

            if(res.isSuccessful) {
               setJWT(res.body()!!.jwt)
            }
        }
    }

    fun setJWT(jwt: String?) {
        liveJwt.postValue(jwt)
        this.jwt = jwt
        prefs.edit().apply {
            putString("JWT", jwt)
        }.apply()
    }

    fun getAuthToken() = jwt

    fun getAuthState(): LiveData<String?> = liveJwt

    fun isLoggedIn() = !jwt.isNullOrEmpty()

    fun logout() {
        setJWT(null)
    }

    fun getUserDetailsImpl(jwt: String): UserClaims {
        val claims = JWT(jwt).claims

        return UserClaims(
            claims["id"]!!.asInt()!!,
            claims["username"]!!.asString()!!,
            //claims["iss"]!!.asString()!!,
            claims["show_nsfw"]!!.asBoolean()!!,
            //claims["theme"]!!.asString()!!,
            claims["default_sort_type"]!!.asInt()!!,
            claims["default_listing_type"]!!.asInt()!!,
            claims["lang"]!!.asString()!!,
            claims["avatar"]!!.asString(),
            claims["show_avatars"]!!.asBoolean()!!
        )
    }

    fun getUserDetails(): UserClaims? = jwt?.let {getUserDetailsImpl(it)}

    fun getLiveUserDetails(): LiveData<UserClaims?> = liveJwt.map { it?.let { it1 -> getUserDetailsImpl(it1) } }

    fun register(username: String, email: String?, password: String, passwordVerify: String) {
        GlobalScope.launch {
            val res = api.register(RegisterRequest(username, email, password, passwordVerify, false))
            println("Register($username) = $res")
            if(res.isSuccessful) {
                setJWT(res.body()!!.jwt)
            }
        }
    }

    fun getLoginState() = getAuthState().map { !it.isNullOrEmpty() }
}