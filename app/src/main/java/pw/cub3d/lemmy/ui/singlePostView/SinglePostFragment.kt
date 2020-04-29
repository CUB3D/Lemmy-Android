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

import pw.cub3d.lemmy.core.networking.CommentView
import pw.cub3d.lemmy.databinding.FragmentSinglePostBinding
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

    private fun addChildNode(
        root: TreeNode<*>,
        child: CommentView,
        comments: Array<CommentView>
    ) {
        val node = TreeNode(CommentItem(child))
        node.javaClass.getDeclaredField("isExpand").apply {
            isAccessible = true
            setBoolean(node, true)
        }
        root.addChild(node)

        val children = comments.filter{ it.parent_id == child.id}

        children.forEach { subChild ->
            addChildNode(node, subChild, comments)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.postId = arguments.postId

        binding.singlePostCommentTree.layoutManager = LinearLayoutManager(requireContext())
        val adapter = TreeViewAdapter(emptyList(), listOf(CommentNodeBinder(viewModel)))
        binding.singlePostCommentTree.adapter = adapter

        viewModel.getPost().observe(viewLifecycleOwner, Observer { post ->
            binding.singlePostTitle.text = post.post.name
            binding.singlePostContent.text = post.post.body

            post.post.url?.let {
                binding.singlePostLink.text = post.post.url
                binding.singlePostLink.setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(post.post.url)))
                }
            }

            val nodes = mutableListOf<TreeNode<*>>()

            // Get the root comments
            post.comments.filter { it.parent_id == null }.forEach { comment ->
                val node = TreeNode<CommentItem>(CommentItem(comment))

//                 Set isExpand
                node.javaClass.getDeclaredField("isExpand").apply {
                    isAccessible = true
                    setBoolean(node, true)
                }

                nodes.add(node)
                // Also add any children
                val children = post.comments.filter { it.parent_id == comment.id }

                children.forEach { child ->
                    addChildNode(node, child, post.comments)
                }
            }

            adapter.refresh(nodes)
        })
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}
