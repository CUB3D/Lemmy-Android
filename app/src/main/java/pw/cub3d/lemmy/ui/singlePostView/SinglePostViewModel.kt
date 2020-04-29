package pw.cub3d.lemmy.ui.singlePostView

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import pw.cub3d.lemmy.core.data.CommentVote
import pw.cub3d.lemmy.core.data.CommentsRepository
import pw.cub3d.lemmy.core.data.PostsRepository
import pw.cub3d.lemmy.core.networking.CommentView
import pw.cub3d.lemmy.core.networking.PostResponse
import pw.cub3d.lemmy.core.networking.PostView
import javax.inject.Inject

class SinglePostViewModel @Inject constructor(
    private val postsRepository: PostsRepository,
    private val commentsRepository: CommentsRepository
): ViewModel() {
    var postId: Int = 0

//    val savedComentId = MutableLiveData<Pair<Int, Boolean>>()
//
//    val commentsResults by lazy {
//        liveData<CommentView>(context = viewModelScope.coroutineContext + Dispatchers.IO) {
//            emitSource(MediatorLiveData<List<CommentView>>().apply {
//                addSource(postsRepository.getPost(postId).map { it.comments}) {
//                    value = it.asList()
//                }
//                addSource(flow {
//                    savedComentId.asFlow().collect {
//                        val r = commentsRepository.saveComment(it.first, it.second)
//                        if (r != null)
//                            emit(r)
//                    }
//                }.flowOn(Dispatchers.IO).asLiveData()) {
//                    value = listOf(it)
//                }
//                addSource(flow {
//                    voteRequest.asFlow().collect {
//                        val r = postsRepository.votePost(it.first, it.second)
//                        if (r != null)
//                            emit(r)
//                    }
//                }.flowOn(Dispatchers.IO).asLiveData()) {
//                    value = it
//                }
//            })
//        }
//    }
//
//
//
//
//
//
//
//    var comments: MediatorLiveData<Array<CommentView>> = MediatorLiveData()
//
//    fun loadPost(postId: Int): LiveData<PostResponse> {
//        this.postId = postId
//        val ld = postsRepository.getPost(postId)
//        return ld
//    }


    fun getPost() = postsRepository.getPost(postId)
    fun unvoteComment(id: Int) {
        GlobalScope.launch {
            commentsRepository.setVote(postId, id, CommentVote.NEUTRAL)
        }
    }

    fun upvoteComment(id: Int) {
        GlobalScope.launch {
            commentsRepository.setVote(postId, id, CommentVote.UPVOTE)
        }
    }

    fun downvoteComment(id: Int) {
        GlobalScope.launch {
            commentsRepository.setVote(postId, id, CommentVote.DOWNVOTE)
        }
    }

    fun unSave(id: Int) {
        GlobalScope.launch {
            commentsRepository.saveComment(id,  false)
        }
    }

    fun saveComment(id: Int) {
        GlobalScope.launch {
            commentsRepository.saveComment(id,  true)
        }
    }
}
