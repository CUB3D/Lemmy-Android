package pw.cub3d.lemmy.core.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pw.cub3d.lemmy.core.networking.LemmyApiInterface
import pw.cub3d.lemmy.core.networking.LoginRequest
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
            val res = api.login(LoginRequest(username, password))

            println("Got login response: $res")

            if(res.isSuccessful) {
               setJWT(res.body()!!.jwt)
            }
        }
    }

    private fun setJWT(jwt: String?) {
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
}