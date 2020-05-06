package pw.cub3d.lemmy.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.databinding.FragmentHomeBinding
import javax.inject.Inject


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    private lateinit var adapter: HomePagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HomePagerAdapter(
            requireContext(),
            requireActivity().supportFragmentManager,
            lifecycle
        )


        viewModel.followedCommunities.observe(viewLifecycleOwner, Observer {
            adapter.updateData(it)

            binding.homeViewpager.adapter = adapter
            TabLayoutMediator(
                binding.homeTabs,
                binding.homeViewpager,
                TabConfigurationStrategy { tab, position ->
                    tab.text = adapter.communities[position].community_name
                }).attach()

            binding.homeViewpager.currentItem = viewModel.selectedTabIndex
        })
    }

    override fun onPause() {
        super.onPause()
        println("Saving ${binding.homeViewpager.currentItem} as selected")
        viewModel.selectedTabIndex = binding.homeViewpager.currentItem
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}
