package pw.cub3d.lemmy.ui.siteinfo

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
import io.noties.markwon.Markwon
import pw.cub3d.lemmy.databinding.FragmentSiteInfoBinding
import pw.cub3d.lemmy.ui.common.userList.UserListAdapter
import javax.inject.Inject

class SiteInfoFragment : Fragment() {

    private lateinit var binding: FragmentSiteInfoBinding
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: SiteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSiteInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)[SiteViewModel::class.java]

        binding.siteAdmins.layoutManager = LinearLayoutManager(requireContext())
        val adapter =
            UserListAdapter(requireContext())
        binding.siteAdmins.adapter = adapter

        viewModel.getSite().observe(viewLifecycleOwner, Observer {
            binding.siteView = it.site
            Markwon.create(requireContext()).setMarkdown(binding.siteDescription, it.site!!.description)
            adapter.updateData(it.admins)
        })
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}

