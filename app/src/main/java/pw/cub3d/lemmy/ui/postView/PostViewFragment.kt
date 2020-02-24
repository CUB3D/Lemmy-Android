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

    @Inject lateinit var postsViewModelFactory: PostsViewModelFactory
    lateinit var postsViewModel: PostsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        postsViewModel = ViewModelProvider(viewModelStore, postsViewModelFactory)
            .get(PostsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_post_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postsViewModel.posts.observe(viewLifecycleOwner, Observer { posts ->
            postView_recycler.layoutManager = LinearLayoutManager(requireContext())
            postView_recycler.adapter = PostViewAdapter(requireActivity(), posts)
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }
}
