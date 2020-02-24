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
    fun getPosts(): LiveData<List<PostView>> {
        val mutableLiveData = MutableLiveData<List<PostView>>()

        GlobalScope.launch {
            lemmyApiInterface.getPosts(limit = 100).body()?.let {
                mutableLiveData.postValue(it.posts)
            }
        }

        return mutableLiveData
    }
}