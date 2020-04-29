package pw.cub3d.lemmy.ui.community

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.AndroidSupportInjection

import pw.cub3d.lemmy.databinding.FragmentCommunityInfoBinding
import pw.cub3d.lemmy.ui.common.userList.UserListAdapter
import javax.inject.Inject

class CommunityInfoFragment : Fragment() {

    private lateinit var binding: FragmentCommunityInfoBinding
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: CommunityViewModel

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
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)[CommunityViewModel::class.java]

        binding.communityAdmins.layoutManager = LinearLayoutManager(requireContext())
        val adapter =
            UserListAdapter(requireContext())
        binding.communityAdmins.adapter = adapter

        viewModel.getSite(arguments.communityId).observe(viewLifecycleOwner, Observer {
            binding.communityView = it.community
            binding.communityDescription.loadMarkdown(it.community.description)
            adapter.updateData(it.admins)
        })
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}
