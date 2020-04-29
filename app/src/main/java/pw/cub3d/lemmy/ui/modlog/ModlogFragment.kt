package pw.cub3d.lemmy.ui.modlog

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.AndroidSupportInjection

import pw.cub3d.lemmy.core.data.ModLogEntry
import pw.cub3d.lemmy.core.data.ModLogEntryType
import pw.cub3d.lemmy.core.networking.*
import pw.cub3d.lemmy.databinding.FragmentModlogBinding
import pw.cub3d.lemmy.databinding.ModlogRemovedpostEntryBinding
import javax.inject.Inject

class ModlogFragment : Fragment() {

    private lateinit var binding: FragmentModlogBinding
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: ModlogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModlogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)[ModlogViewModel::class.java]

        binding.modLogEntries.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ModLogAdapter(requireContext())
        binding.modLogEntries.adapter = adapter

        viewModel.modlogEntries.observe(viewLifecycleOwner, Observer {
            println("Got modlog entry: $it")
            adapter.addEntry(it)
        })

        binding.modLogEntries.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if ((binding.modLogEntries.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() >= adapter.entries.size / 2) {
                    viewModel.page.postValue(viewModel.page.value!! + 1)
                }
            }
        })
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}

class ModLogAdapter(ctx: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val layoutInflater = LayoutInflater.from(ctx)

    val entries = mutableListOf<ModLogEntry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            else -> ModLogRemovedPostViewHolder(ModlogRemovedpostEntryBinding.inflate(layoutInflater, parent, false))
        }
    }

    override fun getItemCount() = entries.size
    override fun getItemViewType(position: Int) = entries[position].type.id

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val entry = entries[position]
        when(entry.type) {
            ModLogEntryType.REMOVED_POST -> (holder as ModLogRemovedPostViewHolder).bind(entry.removePostView!!)
            ModLogEntryType.LOCKED_POST -> (holder as ModLogRemovedPostViewHolder).bind(entry.lockedPostView!!)
            ModLogEntryType.STICKIED_POST -> (holder as ModLogRemovedPostViewHolder).bind(entry.stickiedPostView!!)
            ModLogEntryType.REMOVED_COMMENTS -> (holder as ModLogRemovedPostViewHolder).bind(entry.removedComments!!)
            ModLogEntryType.REMOVED_COMMUNITIES -> (holder as ModLogRemovedPostViewHolder).bind(entry.removedCommunity!!)
            ModLogEntryType.BANNED_FROM_COMMUNITY -> (holder as ModLogRemovedPostViewHolder).bind(entry.bannedFromCommunityView!!)
            ModLogEntryType.BANNED -> (holder as ModLogRemovedPostViewHolder).bind(entry.bannedView!!)
            ModLogEntryType.ADDED_TO_COMMUNITY -> (holder as ModLogRemovedPostViewHolder).bind(entry.addedToCommunityView!!)
            ModLogEntryType.ADDED -> (holder as ModLogRemovedPostViewHolder).bind(entry.addedPostView!!)
        }
    }

    fun addEntry(entry: ModLogEntry) {
        entries.add(entry)
        notifyItemChanged(entries.lastIndex)
    }
}

class ModLogRemovedPostViewHolder(val binding: ModlogRemovedpostEntryBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(removePostView: ModRemovePostView) {
        binding.modUserName = removePostView.mod_user_name
        binding.reason = removePostView.reason
        binding.modLogRemovedPostAction.text = "Removed post \"${removePostView.post_name}\""
    }

    fun bind(post: ModLockPostView) {
        binding.modUserName = post.mod_user_name
        binding.reason = ""
        binding.modLogRemovedPostAction.text = "Locked post \"${post.post_name}\""
    }

    fun bind(post: ModStickiedPostView) {
        binding.modUserName = post.mod_user_name
        binding.reason = ""
        binding.modLogRemovedPostAction.text = "Stickied post in \"${post.community_name}\""
    }

    fun bind(post: ModRemoveCommentView) {
        binding.modUserName = post.mod_user_name
        binding.reason = post.reason
        binding.modLogRemovedPostAction.text = "Removed comment \"${post.comment_content}\""
    }

    fun bind(post: ModRemoveCommunityView) {
        binding.modUserName = post.mod_user_name
        binding.reason = post.reason
        binding.modLogRemovedPostAction.text = "Removed community in \"${post.community_name}\""
    }

    fun bind(post: ModBanFromCommunityView) {
        binding.modUserName = post.other_user_name
        binding.reason = post.reason
        binding.modLogRemovedPostAction.text = "Banned \"${post.other_user_name}\" from \"${post.community_name}\""
    }

    fun bind(post: ModBanView) {
        binding.modUserName = post.other_user_name
        binding.reason = post.reason
        binding.modLogRemovedPostAction.text = "Banned \"${post.other_user_name}\""
    }

    fun bind(post: ModAddCommunityView) {
        binding.modUserName = post.other_user_name
        binding.reason = ""
        binding.modLogRemovedPostAction.text = "Added \"${post.other_user_name}\" to \"${post.community_name}\""
    }

    fun bind(post: ModAddView) {
        binding.modUserName = post.other_user_name
        binding.reason = ""
        binding.modLogRemovedPostAction.text = "Added \"${post.other_user_name}\" as Admin"
    }
}