package pw.cub3d.lemmy.ui.loading

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.android.support.AndroidSupportInjection
import pw.cub3d.lemmy.MainActivity

import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.core.dagger.inject
import pw.cub3d.lemmy.core.data.AuthRepository
import javax.inject.Inject

class LoadingFragment : Fragment() {

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nc = findNavController()
        if(!authRepository.isLoggedIn()) {
            nc.navigate(LoadingFragmentDirections.actionLoadingFragmentToLoginFragment())
        } else {
            nc.navigate(LoadingFragmentDirections.actionLoadingFragmentToHomeFragment())
        }
    }

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
    }

}
