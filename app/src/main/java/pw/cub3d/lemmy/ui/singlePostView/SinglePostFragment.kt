package pw.cub3d.lemmy.ui.singlePostView

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.flow.combine

import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.databinding.FragmentSinglePostBinding
import pw.cub3d.lemmy.ui.login.LoginViewModel_Factory
import tellh.com.recyclertreeview_lib.TreeNode
import tellh.com.recyclertreeview_lib.TreeViewAdapter
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

            post.post.url?.let {
                binding.singlePostLink.setText(post.post.url)
                binding.singlePostLink.setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(post.post.url)))
                }
            }

            val nodes = mutableListOf<TreeNode<*>>()
            (0..10).forEach {
                nodes.add(TreeNode<CommentItem>(CommentItem()))
            }
            binding.singlePostCommentTree.layoutManager = LinearLayoutManager(requireContext())
            binding.singlePostCommentTree.adapter = TreeViewAdapter(nodes, listOf(CommentNodeBinder()))
        })
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}
