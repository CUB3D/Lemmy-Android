package pw.cub3d.lemmy.ui.imageDetail

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import pw.cub3d.lemmy.core.utility.GlideApp
import pw.cub3d.lemmy.databinding.FragmentImageDetailBinding

class ImageDetailFragment : Fragment() {

    private lateinit var binding: FragmentImageDetailBinding

    private val arguments: ImageDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlideApp.with(requireContext())
            .load(Uri.parse(arguments.imageUrl))
            .into(binding.imageDetailImage)
    }
}
