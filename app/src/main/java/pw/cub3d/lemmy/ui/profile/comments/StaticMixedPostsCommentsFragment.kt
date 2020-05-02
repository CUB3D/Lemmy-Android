package pw.cub3d.lemmy.ui.profile.comments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.AndroidSupportInjection

import pw.cub3d.lemmy.core.networking.comment.CommentView
import pw.cub3d.lemmy.core.networking.PostView
import pw.cub3d.lemmy.databinding.FragmentStaticMixedPostsCommentsBinding
import pw.cub3d.lemmy.databinding.PostEntryBinding
import pw.cub3d.lemmy.databinding.ProfileCommentEntryBinding
import pw.cub3d.lemmy.ui.postListView.PostViewHolder
import pw.cub3d.lemmy.ui.postListView.PostsViewModel
import javax.inject.Inject


class StaticMixedPostsCommentsFragment : Fragment() {

    private lateinit var binding: FragmentStaticMixedPostsCommentsBinding
    var entries: List<MixedPosts> = emptyList()

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var postsViewModel: PostsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStaticMixedPostsCommentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postsViewModel = ViewModelProvider(viewModelStore, viewModelFactory)[PostsViewModel::class.java]

        println("Got mixed: ${entries.size}")

        binding.staticMixedRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.staticMixedRecycler.adapter = StaticMixedAdapter(requireContext(), findNavController(), entries, postsViewModel)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }
}

data class MixedPosts(
    val postType: MixedPostType,
    val comment: CommentView?,
    val post: PostView?
)

enum class MixedPostType(val id: Int) {
    POST(0), COMMENT(1)
}

class StaticMixedAdapter(
    private val ctx: Context,
    private val navController: NavController,
    private val entries: List<MixedPosts>,
    private val postsViewModel: PostsViewModel
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val layoutInflater = LayoutInflater.from(ctx)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MixedPostType.POST.id -> PostViewHolder(PostEntryBinding.inflate(layoutInflater, parent, false), navController, postsViewModel)
            MixedPostType.COMMENT.id ->  CommentViewHolder(ProfileCommentEntryBinding.inflate(layoutInflater, parent, false))
            else -> TODO("WIll never happen")
        }
    }

    override fun getItemCount() = entries.size

    override fun getItemViewType(position: Int) = entries[position].postType.id

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val entry = entries[position]
        when(entry.postType) {
            MixedPostType.POST -> (holder as PostViewHolder).bind(entry.post!!)
            MixedPostType.COMMENT -> (holder as CommentViewHolder).bind(entry.comment!!)
        }
    }

}

class CommentViewHolder(val view: ProfileCommentEntryBinding): RecyclerView.ViewHolder(view.root) {
    fun bind(comment: CommentView) {
        view.profileCommentContent.text = comment.content
    }
}