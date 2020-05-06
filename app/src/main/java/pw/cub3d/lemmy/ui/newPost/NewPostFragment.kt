package pw.cub3d.lemmy.ui.newPost

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import pw.cub3d.lemmy.R


import pw.cub3d.lemmy.databinding.FragmentNewPostBinding


class NewPostFragment : Fragment() {

    private lateinit var binding: FragmentNewPostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewPostBinding.inflate(inflater, container, false)
        return binding.root
    }


}
