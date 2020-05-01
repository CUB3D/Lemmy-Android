package pw.cub3d.lemmy.ui.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection

import pw.cub3d.lemmy.R
import pw.cub3d.lemmy.core.data.SearchType
import pw.cub3d.lemmy.databinding.FragmentSearchBinding
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: SearchViewModel

    lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)[SearchViewModel::class.java]

        viewModel.searchResults.observe(viewLifecycleOwner, Observer {
            println("Got search res $it")
        })

        binding.searchSearch.setOnClickListener {
            viewModel.pageLiveData.postValue(1)
            viewModel.searchTypeLiveData.postValue(SearchType.ALL)

            viewModel.queryLiveData.postValue(binding.searchQuery.text.toString())
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}
