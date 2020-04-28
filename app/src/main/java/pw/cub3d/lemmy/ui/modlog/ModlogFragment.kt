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
import pw.cub3d.lemmy.core.networking.ModRemovePostView
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
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}

class ModLogAdapter(ctx: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val layoutInflater = LayoutInflater.from(ctx)

    private val entries = mutableListOf<ModLogEntry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> ModLogRemovedPostViewHolder(ModlogRemovedpostEntryBinding.inflate(layoutInflater, parent, false))
            else -> TODO()
        }
    }

    override fun getItemCount() = entries.size
    override fun getItemViewType(position: Int) = entries[position].type

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            0 -> (holder as ModLogRemovedPostViewHolder).bind(entries[position].removePostView)
        }
    }

    fun addEntry(entry: ModLogEntry) {
        entries.add(entry)
        notifyItemChanged(entries.lastIndex)
    }
}

class ModLogRemovedPostViewHolder(val binding: ModlogRemovedpostEntryBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(removePostView: ModRemovePostView) {
        binding.removedPostView = removePostView
        binding.modLogRemovedPostAction.text = "Removed post \"${removePostView.post_name}\""
    }
}