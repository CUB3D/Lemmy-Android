package pw.cub3d.lemmy.ui.postView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pw.cub3d.lemmy.core.data.PostsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsViewModelFactory @Inject constructor(
    private val postsRepository: PostsRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) = PostsViewModel(postsRepository) as T
}