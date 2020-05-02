package pw.cub3d.lemmy.ui.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.AndroidSupportInjection

import pw.cub3d.lemmy.core.data.SearchResultEntry
import pw.cub3d.lemmy.core.data.SearchResultType
import pw.cub3d.lemmy.core.data.SearchType
import pw.cub3d.lemmy.core.networking.community.CommunityView
import pw.cub3d.lemmy.databinding.*
import pw.cub3d.lemmy.ui.common.userList.UserViewHolder
import pw.cub3d.lemmy.ui.postListView.PostViewHolder
import pw.cub3d.lemmy.ui.postListView.PostsViewModel
import pw.cub3d.lemmy.ui.singlePostView.CommentViewHolder
import pw.cub3d.lemmy.ui.singlePostView.SinglePostViewModel
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: SearchViewModel

    lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)[SearchViewModel::class.java]


        binding.serachResults.layoutManager = LinearLayoutManager(requireContext())
        val adapter = SearchResultsAdapter(requireContext(), ViewModelProvider(viewModelStore, viewModelFactory)[SinglePostViewModel::class.java], findNavController(), ViewModelProvider(viewModelStore, viewModelFactory)[PostsViewModel::class.java])
        binding.serachResults.adapter = adapter

        viewModel.searchResults.observe(viewLifecycleOwner, Observer {
            println("Got search res $it")
            adapter.updateData(it)
        })

        binding.searchSearch.setOnClickListener {
            viewModel.pageLiveData.postValue(1)
            viewModel.searchTypeLiveData.postValue(SearchType.ALL)

            viewModel.queryLiveData.postValue(binding.searchQuery.text.toString())
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}

class SearchResultsAdapter(ctx: Context, val singlePostViewModel: SinglePostViewModel, val navController: NavController, val postViewModel: PostsViewModel): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater = LayoutInflater.from(ctx)
    private val entries = mutableListOf<SearchResultEntry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            SearchResultType.USER.id -> UserViewHolder(UserEntryBinding.inflate(inflater, parent, false))
            SearchResultType.COMMUNITY.id -> CommunityViewHolder(CommunityEntryBinding.inflate(inflater, parent, false))
            SearchResultType.COMMENT.id -> CommentViewHolder(CommentEntryBinding.inflate(inflater, parent, false), singlePostViewModel)
                SearchResultType.POST.id -> PostViewHolder(PostEntryBinding.inflate(inflater, parent, false), navController, postViewModel)
            else -> TODO()
        }
    }

    override fun getItemCount() = entries.size
    override fun getItemViewType(position: Int) = entries[position].type.id

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val entry = entries[position]
        when (entry.type) {
            SearchResultType.COMMENT -> (holder as CommentViewHolder).bind(entry.comment!!)
            SearchResultType.POST -> (holder as PostViewHolder).bind(entry.post!!)
            SearchResultType.COMMUNITY -> (holder as CommunityViewHolder).bind(entry.community!!)
            SearchResultType.USER -> (holder as UserViewHolder).bind(entry.user!!)
        }
    }

    fun updateData(entry: SearchResultEntry) {
        entries.add(entry)
        notifyItemChanged(entries.lastIndex)
    }
}

data class CommunityViewHolder(val view: CommunityEntryBinding): RecyclerView.ViewHolder(view.root) {
    fun bind(community: CommunityView) {
        view.communityView = community
    }
}
