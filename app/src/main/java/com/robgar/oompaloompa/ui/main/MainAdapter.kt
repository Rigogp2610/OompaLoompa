package com.robgar.oompaloompa.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.robgar.oompaloompa.R
import com.robgar.oompaloompa.data.model.Worker
import com.robgar.oompaloompa.ui.inflate
import com.robgar.oompaloompa.ui.loadUrl
import kotlinx.android.synthetic.main.item_worker.view.*
import kotlin.properties.Delegates

private typealias Listener = (Worker) -> Unit

class MainAdapter(
    workers: List<Worker> = emptyList(),
    private val listener: Listener
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var workers: List<Worker> by Delegates.observable(workers) {_, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_worker)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val worker = workers[position]
        holder.bind(worker)

        holder.itemView.setOnClickListener{
            listener(worker)
        }
    }

    override fun getItemCount(): Int = workers.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(worker: Worker) {
            itemView.ivOompaLoompa.loadUrl(worker.image)
            itemView.tvName.text = "${worker.firstName} ${worker.lastName}"
            itemView.tvProfession.text = worker.profession
            itemView.tvGender.text = worker.gender
        }
    }
}