package pw.cub3d.lemmy.ui.siteinfo

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.AndroidSupportInjection

import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.core.networking.GetSiteResponse
import pw.cub3d.lemmy.core.networking.SiteView
import pw.cub3d.lemmy.core.networking.UserView
import pw.cub3d.lemmy.core.utility.GlideApp
import pw.cub3d.lemmy.databinding.FragmentSiteInfoBinding
import pw.cub3d.lemmy.databinding.UserEntryBinding
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
        val adapter = SiteAdminsAdapter(requireContext())
        binding.siteAdmins.adapter = adapter

        viewModel.getSite().observe(viewLifecycleOwner, Observer {
            binding.siteView = it.site
            binding.siteDescription.loadMarkdown(it.site!!.description)
            adapter.updateData(it.admins)
        })
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}

class SiteAdminsAdapter(
    private val ctx: Context
) : RecyclerView.Adapter<UserViewHolder>() {
    private var entries = emptyArray<UserView>()
    private val inflater = LayoutInflater.from(ctx)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(
        UserEntryBinding.inflate(inflater, parent, false)
    )

    override fun getItemCount() = entries.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(entries[position])
    }

    fun updateData(entries: Array<UserView>) {
        this.entries = entries
        notifyDataSetChanged()
    }
}

class UserViewHolder(val view: UserEntryBinding): RecyclerView.ViewHolder(view.root) {
    fun bind(user: UserView) {
        view.userView = user

        if(user.avatar != null) {
            GlideApp.with(view.root)
                .load(Uri.parse(user.avatar))
                .into(view.userEntryAvatar)
        } else {
            GlideApp.with(view.root)
                .load(R.drawable.ic_star)
                .into(view.userEntryAvatar)
        }
    }
}
