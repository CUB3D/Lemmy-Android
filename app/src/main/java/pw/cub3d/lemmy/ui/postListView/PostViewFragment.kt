package pw.cub3d.lemmy.ui.postListView

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_post_view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.core.networking.PostView
import pw.cub3d.lemmy.databinding.FragmentPostViewBinding
import pw.cub3d.lemmy.ui.home.HomeFragmentDirections
import javax.inject.Inject


class PostViewFragment() : Fragment() {

    private lateinit var binding: FragmentPostViewBinding
    @Inject lateinit var postsViewModelFactory: ViewModelProvider.Factory
    val postsViewModel: PostsViewModel by viewModels { postsViewModelFactory }

    private lateinit var postsAdapter: PostViewAdapter

    var community: Int? = null
    var posts: Array<PostView>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPostViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postView_recycler.layoutManager = LinearLayoutManager(requireContext())
        postsAdapter = PostViewAdapter(requireActivity(), findNavController(), postsViewModel)
        postView_recycler.adapter = postsAdapter

        postsViewModel.community.postValue(community)
        postsAdapter.clearData()
        posts?.let {
            it.forEach { postsAdapter.updateData(it) }
        }

        // If given a fixed list of posts then dont page
        if(posts == null) {
            postsViewModel.postResults.observe(viewLifecycleOwner, Observer {
                postsAdapter.updateData(it)
            })


            postView_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if ((postView_recycler.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() >= postsAdapter.posts.size / 2) {
                        postsViewModel.currentPage.postValue(postsViewModel.currentPage.value!! + 1)
                    }
                }
            })
        }

        postsViewModel.bottomSheedState.observe(viewLifecycleOwner, Observer { post ->
            BottomSheetBehavior.from(binding.postViewLongPressActions).state = if(post == null) BottomSheetBehavior.STATE_HIDDEN else BottomSheetBehavior.STATE_EXPANDED

            if(post != null) {
                binding.postViewLongPressGoToUser.text = "/u/${post.creator_name}"

                binding.postViewLongPressGoToCommunity.text = "/c/${post.community_name}"
                binding.postViewLongPressGoToUser.setOnClickListener {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNavGraphProfileFragment(post.creator_id))
                }

                if(post.saved == true) {
                    binding.postViewLongPressSavePost.text = "Unsave"
                    binding.postViewLongPressSavePost.setOnClickListener {
                        postsViewModel.unSave(post)
                    }
                } else {
                    binding.postViewLongPressSavePost.setOnClickListener {
                        postsViewModel.save(post)
                    }
                    binding.postViewLongPressSavePost.text = "Save"
                }
            }
        })

        binding.postViewModLog.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToModlogFragment())
        }

        binding.postViewSiteInfo.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSiteInfoFragment())
        }

        if(community != null) {
            binding.postViewCommunityInfo.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCommunityInfoFragment(community!!))
            }
        } else {
            binding.postViewCommunityInfo.visibility = View.GONE
        }

        binding.postViewInbox.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToInboxFragment())
        }

        binding.postVieWSearch.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }
}
