package com.robgar.oompaloompa.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.robgar.oompaloompa.R
import com.robgar.oompaloompa.data.model.OompaLoompaWorkers
import com.robgar.oompaloompa.ui.detail.DetailActivity
import com.robgar.oompaloompa.ui.startActivity
import com.robgar.oompaloompa.utils.Status
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    companion object {
        const val ID = "id"
    }

    private val viewModel: MainViewModel by viewModel()
    private val adapter = MainAdapter() {
        startActivity<DetailActivity>(ID to it.id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.adapter = adapter

        setupObserver()

        setupScrollView()
    }

    private fun setupObserver() {
        viewModel.oompaLoompaWorkers.observe(this, Observer {
            it?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        recycler.visibility = View.VISIBLE
                        progress.visibility = View.GONE
                        result.data?.let { oompaLoompaWorkers ->
                            viewModel.totalPages = oompaLoompaWorkers.totalPages
                            retrieveData(oompaLoompaWorkers)
                        }
                    } Status.ERROR -> {
                        recycler.visibility = View.VISIBLE
                        progress.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    } Status.LOADING -> {
                        recycler.visibility = View.GONE
                        progress.visibility = View.VISIBLE
                    }
                }
            }
        })
        viewModel.getOompaLoompaWorkers()
    }

    private fun retrieveData(oompaLoompaWorkers: OompaLoompaWorkers) {
        adapter.workers = viewModel.getFilteredWorkers(adapter.workers + oompaLoompaWorkers.workers)
    }

    private fun setupScrollView() {
        recycler.setOnScrollListener((object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.nextPage()
                }
            }
        }))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        adapter.workers = viewModel.getFilteredWorkers(adapter.workers, item.itemId)
        return super.onOptionsItemSelected(item)
    }
}