package pw.cub3d.lemmy.ui.userSettings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection

import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.core.dagger.ViewModelFactory
import pw.cub3d.lemmy.core.dagger.ViewModelKey
import pw.cub3d.lemmy.databinding.FragmentUserSettingsBinding
import javax.inject.Inject

class UserSettingsFragment : Fragment() {

    private lateinit var binding: FragmentUserSettingsBinding
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: UserSettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)[UserSettingsViewModel::class.java]

        viewModel.getUserClaims().observe(viewLifecycleOwner, Observer { user ->
            binding.userSettingsShowNSFW.isChecked = user.show_nsfw
        })

        binding.userSettingsShowNSFW.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.setNSFW(isChecked)
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}
