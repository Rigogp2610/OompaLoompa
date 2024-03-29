package com.robgar.oompaloompa.ui.oompaloompaworker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.robgar.oompaloompa.data.model.OompaLoompaWorker
import com.robgar.oompaloompa.databinding.FragmentOompaLoompaWorkerFragmentBinding
import com.robgar.oompaloompa.ui.loadUrl
import com.robgar.oompaloompa.utils.Result
import org.koin.androidx.viewmodel.ext.android.viewModel

class OompaLoompaWorkerFragment : Fragment() {

    private val viewModel: OompaLoompaWorkerViewModel by viewModel()

    private val args by navArgs<OompaLoompaWorkerFragmentArgs>()

    private lateinit var binding: FragmentOompaLoompaWorkerFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOompaLoompaWorkerFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
    }

    private fun setupObserver() {
        viewModel.oompaLoompaWorker.observe(viewLifecycleOwner) {
            it?.let { result ->
                when (result) {
                    is Result.Success -> {
                        binding.progress.visibility = View.GONE
                        showDataOfOompaLoompaWorker(result.data)
                    }
                    is Result.Error -> {
                        Toast.makeText(requireActivity(), result.message, Toast.LENGTH_LONG).show()
                    }
                    is Result.Loading -> binding.progress.visibility = View.VISIBLE
                }
            }
        }
        viewModel.getOompaLoompaWorker(args.idOompaLoompaWorker)
    }

    private fun showDataOfOompaLoompaWorker(oompaLoompaWorker: OompaLoompaWorker) {
        with(binding) {
            ivOompaLoompa.loadUrl(oompaLoompaWorker.image)
            tvName.text = "${oompaLoompaWorker.firstName} ${oompaLoompaWorker.lastName}"
            tvDescription.text = oompaLoompaWorker.description
            tvProfession.text = oompaLoompaWorker.profession
            tvGender.text = oompaLoompaWorker.gender
            tvCountry.text = oompaLoompaWorker.country
            tvAge.text = "${oompaLoompaWorker.age}"
            tvHeight.text = "${oompaLoompaWorker.height}"
        }
    }
}