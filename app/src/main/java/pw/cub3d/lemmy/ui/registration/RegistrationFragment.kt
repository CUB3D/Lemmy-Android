package pw.cub3d.lemmy.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_registration.*
import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.databinding.FragmentRegistrationBinding


class RegistrationFragment : Fragment() {

    private lateinit var viewModel: RegistrationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this)
            .get(RegistrationViewModel::class.java)

        val binding: FragmentRegistrationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false)
        binding.viewmodel = viewModel
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        register_Register.setOnClickListener {
            viewModel.onRegister()
        }
    }
}
