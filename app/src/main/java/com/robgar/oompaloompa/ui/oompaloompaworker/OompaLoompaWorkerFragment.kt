package com.robgar.oompaloompa.ui.oompaloompaworker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.robgar.oompaloompa.data.model.OompaLoompaWorker
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
        viewModel.oompaLoompaWorker.observe(viewLifecycleOwner, Observer {
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

    private fun retrieveData(oompaLoompaWorker: OompaLoompaWorker) {
        binding.ivOompaLoompa.loadUrl(oompaLoompaWorker.image)
        binding.tvName.text = "${oompaLoompaWorker.firstName} ${oompaLoompaWorker.lastName}"
        binding.tvDescription.text = oompaLoompaWorker.description
        binding.tvProfession.text = oompaLoompaWorker.profession
        binding.tvGender.text = oompaLoompaWorker.gender
        binding.tvCountry.text = oompaLoompaWorker.country
        binding.tvAge.text = oompaLoompaWorker.age.toString()
        binding.tvHeight.text = oompaLoompaWorker.height.toString()
    }
}