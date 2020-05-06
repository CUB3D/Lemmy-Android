package pw.cub3d.lemmy.ui.community

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager

import io.noties.markwon.Markwon
import pw.cub3d.lemmy.core.dagger.injector

import pw.cub3d.lemmy.core.networking.community.CommunityView

import pw.cub3d.lemmy.databinding.FragmentCommunityInfoBinding
import pw.cub3d.lemmy.ui.common.userList.UserListAdapter
import javax.inject.Inject

class CommunityInfoFragment : Fragment() {

    private lateinit var binding: FragmentCommunityInfoBinding
    private val viewModel: CommunityViewModel by viewModels { injector.communityViewModelFactory() }

    private val arguments: CommunityInfoFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.communityAdmins.layoutManager = LinearLayoutManager(requireContext())
        val adapter =
            UserListAdapter(requireContext())
        binding.communityAdmins.adapter = adapter

        viewModel.getSite(arguments.communityId).observe(viewLifecycleOwner, Observer {
            binding.communityView = it.community
            Markwon.create(requireContext()).setMarkdown(binding.communityDescription, it.community.description ?: "")

            adapter.updateData(it.admins)

            setupFollowButton(it.community)
        })

        viewModel.followResults.observe(viewLifecycleOwner, Observer {
            setupFollowButton(it)
        })
    }

    fun setupFollowButton(community: CommunityView) {
        if(community.subscribed == true) {
            binding.communityInfoFollow.setOnClickListener {
                viewModel.communityFollowRequest.postValue(arguments.communityId to true)
            }
        } else {
            binding.communityInfoFollow.setOnClickListener {
                viewModel.communityFollowRequest.postValue(arguments.communityId to false)
            }
            binding.communityInfoFollow.text = "Unfollow"
        }
    }


}
