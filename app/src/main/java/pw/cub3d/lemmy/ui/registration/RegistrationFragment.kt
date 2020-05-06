package pw.cub3d.lemmy.ui.registration

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

import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.core.dagger.injector

import pw.cub3d.lemmy.databinding.FragmentRegistrationBinding
import javax.inject.Inject


class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding

    private val viewModel: RegistrationViewModel by viewModels { injector.registrationViewModelFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerRegister.setOnClickListener {
            viewModel.onRegister()
        }

        viewModel.getAuthState().observe(viewLifecycleOwner, Observer { loggedIn ->
            if(loggedIn) {
                findNavController().navigate(R.id.loadingFragment)
            }
        })
    }


}
