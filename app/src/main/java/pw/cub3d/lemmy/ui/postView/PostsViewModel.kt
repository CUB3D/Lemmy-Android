package pw.cub3d.lemmy.ui.postView

import androidx.lifecycle.ViewModel
import pw.cub3d.lemmy.core.data.PostsRepository

class PostsViewModel(
    private val postsRepository: PostsRepository
): ViewModel() {
    fun getNextPage() = postsRepository.getNextPage()

    val posts = postsRepository.getPosts()
}
