package pw.cub3d.lemmy.ui.profile

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import pw.cub3d.lemmy.core.dagger.injector


import pw.cub3d.lemmy.core.utility.GlideApp
import pw.cub3d.lemmy.databinding.FragmentProfileBinding
import javax.inject.Inject

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels { injector.profileViewModelFactory() }

    private val arguments: ProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments.profileId.takeIf { it != -1 } ?: viewModel.getUserClaims().id

        viewModel.getUserDetails(userId).observe(viewLifecycleOwner, Observer { user ->
            binding.user = user.user
            user.user.avatar?.let {
                GlideApp.with(requireContext())
                    .load(Uri.parse(it))
                    .into(binding.imageView)
            }
            if (user.user.avatar == null) binding.imageView.visibility = View.GONE

            val adapter = ProfilePagerAdapter(
                requireActivity().supportFragmentManager,
                lifecycle,
                user.posts,
                user.comments
            )
            binding.profilePager.adapter = adapter
            TabLayoutMediator(binding.profileTabs, binding.profilePager, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = arrayOf("Overview", "Comments", "Posts", "Saved")[position]
            }).attach()
        })
    }
}
