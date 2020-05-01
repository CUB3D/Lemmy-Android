package pw.cub3d.lemmy.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import pw.cub3d.lemmy.core.networking.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val api: LemmyApiInterface,
    private val authRepository: AuthRepository
) {
    fun search(
        queryLiveData: LiveData<String>,
        searchType: LiveData<SearchType>,
        page: LiveData<Long?>
    ): Flow<SearchResultEntry> = flow {
        queryLiveData.asFlow().collect { query ->
            searchType.asFlow().collect { searchType ->
                page.asFlow().collect { pageNum ->
                    val r = api.search(SearchRequest(query, searchType.id, null, SortType.HOT.id, pageNum, null, authRepository.getAuthToken()!!))
                    println("Search($query, $searchType, $pageNum) = $r")
                    if(r.isSuccessful) {
                        val b = r.body()!!
                        val entries =
                        b.comments.map { SearchResultEntry(SearchResultType.COMMENT, comment = it) } +
                        b.posts.map { SearchResultEntry(SearchResultType.POST, post = it) } +
                        b.communities.map { SearchResultEntry(SearchResultType.COMMUNITY, community = it) } +
                        b.users.map { SearchResultEntry(SearchResultType.USER, user = it) }

                        entries.forEach { emit(it) }
                    }
                }
            }
        }
    }
}

data class SearchResultEntry(
    val type: SearchResultType,
    val comment: CommentView? = null,
    val post: PostView? = null,
    val community: CommunityView? = null,
    val user: UserView? = null
)

enum class SearchResultType(val id: Int) {
    COMMENT(0),
    POST(1),
    COMMUNITY(2),
    USER(3)
}

enum class SearchType(val id: String) {
    ALL("All"),
    COMMENTS("comments"),
    POSTS("Posts"),
    COMMUNITIES("Communities"),
    USERS("Users"),
    URL("Url")
}