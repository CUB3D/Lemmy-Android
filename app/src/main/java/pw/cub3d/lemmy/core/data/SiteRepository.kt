package pw.cub3d.lemmy.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pw.cub3d.lemmy.core.networking.GetSiteResponse
import pw.cub3d.lemmy.core.networking.LemmyApiInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SiteRepository @Inject constructor(
    private val api: LemmyApiInterface
) {
    fun getSiteInfo(): LiveData<GetSiteResponse> {
        val ld = MutableLiveData<GetSiteResponse>()
        GlobalScope.launch {
            val res = api.getSite()
            println("GetSite() = $res")
            if (res.isSuccessful) {
                ld.postValue(res.body()!!)
            }
        }
        return ld
    }
}