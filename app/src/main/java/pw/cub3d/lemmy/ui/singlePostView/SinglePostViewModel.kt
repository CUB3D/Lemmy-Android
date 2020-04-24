package pw.cub3d.lemmy.ui.singlePostView

import androidx.lifecycle.ViewModel
import pw.cub3d.lemmy.core.data.PostsRepository
import javax.inject.Inject

class SinglePostViewModel @Inject constructor(
    private val postsRepository: PostsRepository
): ViewModel() {
    fun getPost(id: Int) = postsRepository.getPost(id)
}
