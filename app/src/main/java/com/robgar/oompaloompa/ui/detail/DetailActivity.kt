package com.robgar.oompaloompa.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.robgar.oompaloompa.R
import com.robgar.oompaloompa.data.model.OompaLoompaWorkers
import com.robgar.oompaloompa.data.model.Worker
import com.robgar.oompaloompa.ui.loadUrl
import com.robgar.oompaloompa.ui.main.MainActivity
import com.robgar.oompaloompa.ui.main.MainViewModel
import com.robgar.oompaloompa.utils.Status
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setupObserver()
    }

    private fun setupObserver() {
        viewModel.worker.observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { worker ->
                            retrieveData(worker)
                        }
                    } Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                } Status.LOADING -> { }
                }
            }
        })
        viewModel.getWorker(intent.getIntExtra(MainActivity.ID, -1))
    }

    private fun retrieveData(worker: Worker) {
        ivOompaLoompa.loadUrl(worker.image)
        tvName.text = "${worker.firstName} ${worker.lastName}"
        tvDescription.text = worker.description
        tvProfession.text = worker.profession
        tvGender.text = worker.gender
        tvCountry.text = worker.country
        tvAge.text = worker.age.toString()
        tvHeight.text = worker.height.toString()
    }
}