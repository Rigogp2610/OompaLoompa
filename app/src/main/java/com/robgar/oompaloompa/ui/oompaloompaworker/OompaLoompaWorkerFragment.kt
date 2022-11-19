package com.robgar.oompaloompa.ui.oompaloompaworker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.robgar.oompaloompa.data.model.Worker
import com.robgar.oompaloompa.databinding.FragmentOompaLoompaWorkerFragmentBinding
import com.robgar.oompaloompa.ui.loadUrl
import com.robgar.oompaloompa.utils.Status
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
        viewModel.worker.observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        result.data?.let { worker ->
                            retrieveData(worker)
                        }
                    } Status.ERROR -> {
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
                } Status.LOADING -> { }
                }
            }
        })
        viewModel.getWorker(args.idOompaLoompaWorker)
    }

    private fun retrieveData(worker: Worker) {
        binding.ivOompaLoompa.loadUrl(worker.image)
        binding.tvName.text = "${worker.firstName} ${worker.lastName}"
        binding.tvDescription.text = worker.description
        binding.tvProfession.text = worker.profession
        binding.tvGender.text = worker.gender
        binding.tvCountry.text = worker.country
        binding.tvAge.text = worker.age.toString()
        binding.tvHeight.text = worker.height.toString()
    }
}