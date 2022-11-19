package com.robgar.oompaloompa.ui.oompaloompaworkers

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.robgar.oompaloompa.R
import com.robgar.oompaloompa.data.model.Worker
import com.robgar.oompaloompa.databinding.ItemWorkerBinding
import com.robgar.oompaloompa.ui.inflate
import com.robgar.oompaloompa.ui.loadUrl
import kotlin.properties.Delegates

private typealias Listener = (Worker) -> Unit

class OompaLoompaWorkersAdapter(
    workers: List<Worker> = emptyList(),
    private val listener: Listener
) : RecyclerView.Adapter<OompaLoompaWorkersAdapter.ViewHolder>() {

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

        private var binding = ItemWorkerBinding.bind(itemView)

        fun bind(worker: Worker) {
            binding.ivOompaLoompa.loadUrl(worker.image)
            binding.tvName.text = "${worker.firstName} ${worker.lastName}"
            binding.tvProfession.text = worker.profession
            binding.tvGender.text = worker.gender
        }
    }
}