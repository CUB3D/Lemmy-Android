package pw.cub3d.lemmy.ui.inbox

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.AndroidSupportInjection

import pw.cub3d.lemmy.databinding.FragmentInboxBinding

class InboxFragment : Fragment() {

    private lateinit var binding: FragmentInboxBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInboxBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = InboxPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        binding.inboxPager.adapter = adapter
        TabLayoutMediator(binding.inboxTabs, binding.inboxPager, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            //TODO: currently not possible to get dm's from rest api for now
            tab.text = arrayOf("All", "Replies", "Mentions")[position]
        }).attach()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}
