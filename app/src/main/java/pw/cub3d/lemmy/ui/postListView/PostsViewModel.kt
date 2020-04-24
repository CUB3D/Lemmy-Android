package pw.cub3d.lemmy.ui.postListView

import androidx.lifecycle.ViewModel
import pw.cub3d.lemmy.core.data.PostsRepository
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    private val postsRepository: PostsRepository
): ViewModel() {
    fun getNextPage() = postsRepository.getNextPage()

    val posts = postsRepository.getPosts()
}
