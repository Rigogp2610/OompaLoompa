package com.robgar.oompaloompa.ui.oompaloompaworkers

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.robgar.oompaloompa.R
import com.robgar.oompaloompa.data.model.OompaLoompaWorkers
import com.robgar.oompaloompa.databinding.FragmentOompaLoompaWorkersFragmentBinding
import com.robgar.oompaloompa.ui.filter_dialog.FilterDialog
import com.robgar.oompaloompa.ui.filter_dialog.FilterType
import com.robgar.oompaloompa.ui.main.OompaLoompaWorkersViewModel
import com.robgar.oompaloompa.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class OompaLoompaWorkersFragment : Fragment() {

    private val viewModel: OompaLoompaWorkersViewModel by viewModel()
    private val adapter = OompaLoompaWorkersAdapter {
        val action = OompaLoompaWorkersFragmentDirections.actionOompaLoompaWorkersFragmentToOompaLoompaWorkerFragment(
            it.id
        )
        findNavController().navigate(action)
    }

    private lateinit var binding : FragmentOompaLoompaWorkersFragmentBinding

    private lateinit var filterDialog: FilterDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOompaLoompaWorkersFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycler.adapter = adapter

        setupObserver()

        setupScrollView()

        filterDialog = FilterDialog(requireActivity()) { filterResponse(it) }

        setupMenu()
    }

    private fun setupObserver() {
        viewModel.oompaLoompaWorkers.observe(viewLifecycleOwner) { value ->
            value?.handle { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        binding.recycler.visibility = View.VISIBLE
                        binding.progress.visibility = View.GONE
                        binding.progressPagination.visibility = View.GONE
                        result.data?.let { oompaLoompaWorkers ->
                            retrieveData(oompaLoompaWorkers)
                        }
                    }
                    Status.ERROR -> {
                        binding.recycler.visibility = View.VISIBLE
                        binding.progress.visibility = View.GONE
                        binding.progressPagination.visibility = View.GONE
                        Toast.makeText(requireActivity(), result.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        if (viewModel.getCurrentPage() == viewModel.firstPage) {
                            binding.recycler.visibility = View.GONE
                            binding.progress.visibility = View.VISIBLE
                        } else {
                            binding.progressPagination.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
        viewModel.getOompaLoompaWorkers()
    }

    private fun retrieveData(oompaLoompaWorkers: OompaLoompaWorkers) {
        adapter.oompaLoompaWorkers = oompaLoompaWorkers.oompaLoompaWorkers
    }

    private fun setupScrollView() {
        binding.recycler.setOnScrollChangeListener((object : RecyclerView.OnScrollListener(),
            View.OnScrollChangeListener {

            override fun onScrollChange(p0: View?, p1: Int, p2: Int, p3: Int, p4: Int) {
                if (!binding.recycler.canScrollVertically(1)) {
                    viewModel.nextPage()
                }
            }
        }))
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.filter -> {
                        filterDialog.show()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun filterResponse(filterList: List<Pair<FilterType, String>>) {
        viewModel.setFilterList(filterList)
        viewModel.showFilteredOompaLoompaWorkers()
    }
}