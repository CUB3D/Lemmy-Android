package pw.cub3d.lemmy.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pw.cub3d.lemmy.core.networking.LemmyApiInterface
import pw.cub3d.lemmy.core.networking.PostView
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsRepository @Inject constructor(
    private val lemmyApiInterface: LemmyApiInterface
) {
    private var page = 1

    private val mutableLiveData = MutableLiveData<List<PostView>>()

    fun getPosts(): LiveData<List<PostView>> {
        page = 1
        getCurrentPage()

        return mutableLiveData
    }

    fun getCurrentPage() {
        GlobalScope.launch {
            lemmyApiInterface.getPosts(page = page, limit = null).body()?.let {
                mutableLiveData.postValue(it.posts)
            }
        }
    }

    fun getNextPage() {
        page += 1
        getCurrentPage()
    }
}