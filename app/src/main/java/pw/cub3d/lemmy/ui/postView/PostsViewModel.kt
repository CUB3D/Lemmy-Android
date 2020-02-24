package pw.cub3d.lemmy.ui.postView

import androidx.lifecycle.ViewModel
import pw.cub3d.lemmy.core.data.PostsRepository

class PostsViewModel(
    postsRepository: PostsRepository
): ViewModel() {
    val posts = postsRepository.getPosts()
}
