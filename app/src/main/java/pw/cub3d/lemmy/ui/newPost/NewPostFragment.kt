package pw.cub3d.lemmy.ui.newPost

import android.content.Context
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController


import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.core.dagger.injector


import pw.cub3d.lemmy.databinding.FragmentNewPostBinding


class NewPostFragment : Fragment() {

    private lateinit var binding: FragmentNewPostBinding


    private val newPostViewModel: NewPostViewModel by viewModels { injector.newPostViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.newPostPost.setOnClickListener {
            newPostViewModel.sendPost(
                binding.newPostTitle.text.toString(),
                binding.newPostUrl.text.toString().takeIf { it.isNotEmpty() },
                binding.newPostBody.text.toString().takeIf { it.isNotEmpty() },
                -1
            )
        }

        newPostViewModel.submitedPostLiveData.observe(viewLifecycleOwner, Observer {
            binding.newPostTitle.transitionName = "${it.id}_title"

            findNavController().navigate(NewPostFragmentDirections.actionNewPostFragmentToSinglePostFragment(it.id, it.name), FragmentNavigatorExtras(
                binding.newPostTitle to "${it.id}_title"
            ))
        })
    }
}
