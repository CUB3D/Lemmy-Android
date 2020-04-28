package pw.cub3d.lemmy.ui.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.android.support.AndroidSupportInjection
import pw.cub3d.lemmy.R

import pw.cub3d.lemmy.databinding.FragmentLoginBinding
import javax.inject.Inject

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)[LoginViewModel::class.java]

        binding.loginUsername.requestFocus()

        binding.loginLogin.setOnClickListener {
            viewModel.login(binding.loginUsername.text.toString(), binding.loginPassword.text.toString())
        }

        viewModel.getAuthState().observe(viewLifecycleOwner, Observer { loggedIn ->
            println("Auth state changed to $loggedIn")
            if(loggedIn) {
                findNavController().navigate(R.id.loadingFragment)
            }
        })

        binding.profileRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}
