package com.robgar.oompaloompa.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.robgar.oompaloompa.R
import com.robgar.oompaloompa.data.model.OompaLoompaWorkers
import com.robgar.oompaloompa.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.oompaLoompaWorkers.observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { oompaLoompaWorkers -> retrieveData(oompaLoompaWorkers) }
                    }
                    Status.ERROR -> {
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
        viewModel.getOompaLoompaWorkers()
    }

    private fun retrieveData(oompaLoompaWorkers: OompaLoompaWorkers) {

    }
}