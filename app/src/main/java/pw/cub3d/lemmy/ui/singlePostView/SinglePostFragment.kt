package pw.cub3d.lemmy.ui.singlePostView

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.flow.combine

import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.databinding.FragmentSinglePostBinding
import pw.cub3d.lemmy.ui.login.LoginViewModel_Factory
import javax.inject.Inject

class SinglePostFragment : Fragment() {

    private val arguments: SinglePostFragmentArgs by navArgs()

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    val viewModel: SinglePostViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentSinglePostBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSinglePostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPost(arguments.postId).observe(viewLifecycleOwner, Observer { post ->
            binding.singlePostTitle.setText(post.post.body)
        })
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}
