package pw.cub3d.lemmy.ui.inbox.inboxTab

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.AndroidSupportInjection
import io.noties.markwon.Markwon
import kotlinx.android.synthetic.main.fragment_post_view.*
import pw.cub3d.lemmy.core.dagger.inject

import pw.cub3d.lemmy.core.data.InboxEntry
import pw.cub3d.lemmy.core.data.InboxEntryType
import pw.cub3d.lemmy.core.networking.ReplyView
import pw.cub3d.lemmy.core.networking.UserMentionView
import pw.cub3d.lemmy.databinding.FragmentInboxTabBinding
import pw.cub3d.lemmy.databinding.InboxCommentEntryBinding
import javax.inject.Inject

class InboxTabFragment : Fragment() {

    private lateinit var binding: FragmentInboxTabBinding
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    val viewModel: InboxTabViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInboxTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inboxTabRecycler.layoutManager = LinearLayoutManager(requireContext())
        val adapter = InboxEntryRecycler(requireContext(), viewModel)
        binding.inboxTabRecycler.adapter = adapter

        viewModel.inboxResults.observe(viewLifecycleOwner, Observer {
            adapter.updateData(it)
        })

        binding.inboxTabRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if ((binding.inboxTabRecycler.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() >= adapter.itemCount - 1) {
                    viewModel.page.postValue(viewModel.page.value!! + 1)
                }
            }
        })

        binding.inboxMarkAllAsRead.setOnClickListener {
            viewModel.markAllAsRead()
        }
    }

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
    }
}

class InboxEntryRecycler(
    ctx: Context,
    private val viewModel: InboxTabViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val layoutInflater = LayoutInflater.from(ctx)
    private var entries = mutableListOf<InboxEntry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            InboxEntryType.REPLY.id, InboxEntryType.MENTION.id -> InboxCommentViewHolder(InboxCommentEntryBinding.inflate(layoutInflater, parent, false), viewModel)
            InboxEntryType.MESSAGE.id -> TODO()
            else -> TODO("WIll never occur")
        }
    }

    override fun getItemCount() = entries.size
    override fun getItemViewType(position: Int) = entries[position].type.id

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val entry = entries[position]
        when(entry.type) {
            InboxEntryType.REPLY -> (holder as InboxCommentViewHolder).bind(entry.reply!!)
            InboxEntryType.MENTION -> (holder as InboxCommentViewHolder).bind(entry.mention!!)
            InboxEntryType.MESSAGE -> TODO()
        }
    }

    fun updateData(data: InboxEntry) {
        this.entries.add(data)
        notifyItemChanged(this.entries.lastIndex)
    }

}

class InboxCommentViewHolder(
    val view: InboxCommentEntryBinding,
    val viewModel: InboxTabViewModel
): RecyclerView.ViewHolder(view.root) {
    fun bind(reply: ReplyView) {
        Markwon.create(view.root.context).setMarkdown(view.inboxCommentContent, reply.content)
        if(reply.read) {
            view.root.setOnClickListener {
                viewModel.markAsRead(reply.id, false)
            }
            view.root.setBackgroundColor(Color.RED)
        } else {
            view.root.setOnClickListener {
                viewModel.markAsRead(reply.id, true)
            }
            view.root.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    fun bind(mention: UserMentionView) {
        Markwon.create(view.root.context).setMarkdown(view.inboxCommentContent, mention.content)
        if(mention.read) {
            view.root.setOnClickListener {
                viewModel.markAsRead(mention.id, false)
            }
            view.root.setBackgroundColor(Color.RED)
        } else {
            view.root.setOnClickListener {
                viewModel.markAsRead(mention.id, true)
            }
            view.root.setBackgroundColor(Color.TRANSPARENT)
        }
    }
}
