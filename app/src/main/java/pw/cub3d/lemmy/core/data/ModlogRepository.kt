package pw.cub3d.lemmy.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import pw.cub3d.lemmy.core.networking.LemmyApiInterface
import pw.cub3d.lemmy.core.networking.ModRemovePostView
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModlogRepository @Inject constructor(
    private val api: LemmyApiInterface
) {
    fun getModlog(
        page: LiveData<Long>
    ): Flow<ModLogEntry> = flow {
        page.asFlow().collect { pageNum ->
            val res = api.getModLog(null, null, pageNum, null)
            println("Got modlog(page = ${page.value}) = $res")
            if(res.isSuccessful) {
                val b = res.body()!!
                b.removed_posts.map {
                    ModLogEntry(0, it)
                }.forEach {
                    emit(it)
                }
            }
        }
    }
}

data class ModLogEntry(
    val type: Int,
    val removePostView: ModRemovePostView
)