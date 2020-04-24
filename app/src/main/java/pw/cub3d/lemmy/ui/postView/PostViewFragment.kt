package pw.cub3d.lemmy.ui.postView

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_post_view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.core.networking.LemmyApiInterface
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject


class PostViewFragment : Fragment() {

    private lateinit var postsAdapter: PostViewAdapter
    @Inject lateinit var postsViewModelFactory: PostsViewModelFactory
    lateinit var postsViewModel: PostsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        postsViewModel = ViewModelProvider(viewModelStore, postsViewModelFactory)
            .get(PostsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_post_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postView_recycler.layoutManager = LinearLayoutManager(requireContext())
        postsAdapter = PostViewAdapter(requireActivity())
        postView_recycler.adapter = postsAdapter

        postsViewModel.posts.observe(viewLifecycleOwner, Observer { posts ->
            println("Got ${posts.size} posts")
            postsAdapter.updateData(posts)
        })

        postView_recycler.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if((postView_recycler.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() == postsAdapter.posts.size - 1){
                    postsViewModel.getNextPage()
                }
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }
}
