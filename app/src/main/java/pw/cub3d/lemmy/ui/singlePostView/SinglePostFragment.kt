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
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

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

    private suspend fun addChildNode(
        root: TreeNode<*>,
        child: CommentView,
        comments: Array<CommentView>,
        depth: Int = 1
    ) {
        val node = TreeNode(CommentItem(child))

        if(depth < 3) node.toggle()

        root.addChild(node)

        comments.filter{ it.parent_id == child.id}.forEach { subChild ->
            addChildNode(node, subChild, comments, depth + 1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.singlePostTitle.transitionName = "${arguments.postId}_title"
        binding.singlePostTitle.text = arguments.postTitle

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


            GlobalScope.launch(Dispatchers.Default) {
                val sortedComments = post.comments.sortedByDescending { it.score }
                val rootComments = sortedComments.filter { it.parent_id == null }
                val childComments = sortedComments.filter { it.parent_id != null }

                val pendingRootNodes = rootComments.map { comment ->
                    GlobalScope.async {
                        val rootCommentNode = TreeNode<CommentItem>(CommentItem(comment))

                        rootCommentNode.toggle()

                        // Also add any children
                        val children = childComments.filter { it.parent_id == comment.id }

                        children.forEach { child ->
                            addChildNode(rootCommentNode, child, post.comments)
                        }

                        rootCommentNode
                    }
                }

                val nodes: List<TreeNode<*>> = pendingRootNodes.awaitAll()
                println("Done loading nodes")

                val m = adapter.javaClass.getDeclaredMethod("findDisplayNodes", List::class.java)
                m.isAccessible = true
                m.invoke(adapter, nodes)

                requireActivity().runOnUiThread {
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}
