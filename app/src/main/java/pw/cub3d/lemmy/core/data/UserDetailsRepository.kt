package pw.cub3d.lemmy.core.data

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pw.cub3d.lemmy.core.networking.LemmyApiInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDetailsRepository @Inject constructor(
    private val api: LemmyApiInterface
) {
    fun getUserDetails(userId: Int) {
        GlobalScope.launch {

        }
    }
}