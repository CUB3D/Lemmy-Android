package pw.cub3d.lemmy.ui.postListView

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_post_view.*
import pw.cub3d.lemmy.R
import javax.inject.Inject


class PostViewFragment : Fragment() {

    @Inject lateinit var postsViewModelFactory: ViewModelProvider.Factory
    lateinit var postsViewModel: PostsViewModel

    private lateinit var postsAdapter: PostViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        postsViewModel = ViewModelProvider(viewModelStore, postsViewModelFactory)[PostsViewModel::class.java]

        return inflater.inflate(R.layout.fragment_post_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postView_recycler.layoutManager = LinearLayoutManager(requireContext())
        postsAdapter = PostViewAdapter(requireActivity(), findNavController())
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
