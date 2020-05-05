package pw.cub3d.lemmy.ui.imageDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pw.cub3d.lemmy.core.utility.GlideApp
import pw.cub3d.lemmy.databinding.FragmentImageDetailBinding
import pw.cub3d.lemmy.ui.home.HomeFragmentDirections
import java.io.BufferedReader
import java.io.File
import java.net.URL

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

        binding.imageUrl = arguments.imageUrl

        binding.imageDetailOpenExternal.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(arguments.imageUrl))) }
        binding.imageDetailShareUrl.setOnClickListener {
            ShareCompat.IntentBuilder
                .from(requireActivity())
                .setText(arguments.imageUrl)
                .setType("text/plain")
                .setChooserTitle("Share URL")
                .startChooser()
        }
        binding.imageDetailShareImage.setOnClickListener {
            ShareCompat.IntentBuilder
                .from(requireActivity())
                .setStream(Uri.parse(arguments.imageUrl))
                    //TODO: detect this mime type
                .setType("image/jpg")
                .setChooserTitle("Share Image")
                .startChooser()
        }

        arguments.postId.takeIf { it != -1 }?.let { postId ->
            binding.imageDetailOpenPost.setOnClickListener { findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSinglePostFragment(postId)) }
            binding.imageDetailOpenPost.visibility = View.VISIBLE
        }


        val moreSheet = BottomSheetBehavior.from(binding.imageDetailBottomSheet)
        moreSheet.state = BottomSheetBehavior.STATE_COLLAPSED

        binding.imageDetailMore.setOnClickListener {
            if(moreSheet.state == BottomSheetBehavior.STATE_COLLAPSED) {
                moreSheet.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                moreSheet.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        binding.imageDetailSaveImage.setOnClickListener {
            saveImage()
        }

        binding.imageDetailSaveImage2.setOnClickListener {
            saveImage()
        }



        GlideApp.with(requireContext())
            .load(Uri.parse(arguments.imageUrl))
            .into(binding.imageDetailImage)
    }

    fun saveImage() {
        GlobalScope.launch(Dispatchers.IO) {
            val inputStream = URL(arguments.imageUrl).openConnection().getInputStream()
            val bytes = inputStream.readBytes()

            val baseFile = File("/sdcard/Download/").apply { mkdirs() }

            File(baseFile, arguments.imageUrl.split("//")[1].replace("/", "_")).writeBytes(bytes)
        }
    }
}
