package pw.cub3d.lemmy.ui.userSettings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.core.dagger.ViewModelFactory
import pw.cub3d.lemmy.core.dagger.ViewModelKey
import pw.cub3d.lemmy.core.dagger.injector

import pw.cub3d.lemmy.databinding.FragmentUserSettingsBinding
import javax.inject.Inject

class UserSettingsFragment : Fragment() {

    private lateinit var binding: FragmentUserSettingsBinding

    private val viewModel: UserSettingsViewModel by viewModels { injector.userSettingsViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserClaims().observe(viewLifecycleOwner, Observer { user ->
            binding.userSettingsShowNSFW.isChecked = user.show_nsfw
        })

        binding.userSettingsShowNSFW.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.setNSFW(isChecked)
        }
    }


}
