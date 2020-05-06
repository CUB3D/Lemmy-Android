package pw.cub3d.lemmy.ui.singlePostView

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
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
import io.noties.markwon.Markwon

import pw.cub3d.lemmy.core.networking.comment.CommentView
import pw.cub3d.lemmy.databinding.FragmentSinglePostBinding
import tellh.com.recyclertreeview_lib.TreeNode
import tellh.com.recyclertreeview_lib.TreeViewAdapter
import javax.inject.Inject

class SinglePostFragment : Fragment() {

    private val arguments: SinglePostFragmentArgs by navArgs()

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    val viewModel: SinglePostViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentSinglePostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

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

        children.sortedByDescending { it.score }.forEach { subChild ->
            addChildNode(node, subChild, comments)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.singlePostTitle.transitionName = "${arguments.postId}_title"

        viewModel.postId = arguments.postId

        binding.singlePostCommentTree.layoutManager = LinearLayoutManager(requireContext())
        val adapter = TreeViewAdapter(emptyList(), listOf(CommentNodeBinder(viewModel)))
        binding.singlePostCommentTree.adapter = adapter
        binding.singlePostCommentTree.isNestedScrollingEnabled = false

        viewModel.getPost().observe(viewLifecycleOwner, Observer { post ->
            binding.singlePostTitle.text = post.post.name

            if(post.post.body != null) {
                Markwon.create(view.context).setMarkdown(binding.singlePostContent, post.post.body)
            } else {
                binding.singlePostContent.visibility = View.GONE
            }

            post.post.url?.let {
                binding.singlePostLinkHolder.visibility = View.VISIBLE
                binding.singlePostLinkDomain.text = Uri.parse(post.post.url).host
                binding.singlePostLinkHolder.setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(post.post.url)))
                }
            }

            val nodes = mutableListOf<TreeNode<*>>()

            // Get the root comments
            post.comments.filter { it.parent_id == null }.sortedByDescending { it.score }.forEach { comment ->
                val node = TreeNode<CommentItem>(CommentItem(comment))

//                 Set isExpand
                node.javaClass.getDeclaredField("isExpand").apply {
                    isAccessible = true
                    setBoolean(node, true)
                }

                nodes.add(node)
                // Also add any children
                val children = post.comments.filter { it.parent_id == comment.id }

                children.sortedByDescending { it.score }.forEach { child ->
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
