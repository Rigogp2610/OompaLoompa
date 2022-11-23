package com.robgar.oompaloompa.ui.oompaloompaworkers

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.robgar.oompaloompa.R
import com.robgar.oompaloompa.data.model.OompaLoompaWorker
import com.robgar.oompaloompa.databinding.ItemOompaLoompaWorkerBinding
import com.robgar.oompaloompa.ui.inflate
import com.robgar.oompaloompa.ui.loadUrl
import kotlin.properties.Delegates

private typealias Listener = (OompaLoompaWorker) -> Unit

class OompaLoompaWorkersAdapter(
    oompaLoompaWorkers: List<OompaLoompaWorker> = emptyList(),
    private val listener: Listener
) : RecyclerView.Adapter<OompaLoompaWorkersAdapter.ViewHolder>() {

    var oompaLoompaWorkers: List<OompaLoompaWorker> by Delegates.observable(oompaLoompaWorkers) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_oompa_loompa_worker)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val worker = oompaLoompaWorkers[position]
        holder.bind(worker)

        holder.itemView.setOnClickListener{
            listener(worker)
        }
    }

    override fun getItemCount(): Int = oompaLoompaWorkers.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var binding = ItemOompaLoompaWorkerBinding.bind(itemView)

        fun bind(oompaLoompaWorker: OompaLoompaWorker) {
            with(binding) {
                ivOompaLoompa.loadUrl(oompaLoompaWorker.image)
                tvName.text = "${oompaLoompaWorker.firstName} ${oompaLoompaWorker.lastName}"
                tvProfession.text = oompaLoompaWorker.profession
                tvGender.text = oompaLoompaWorker.gender
            }
        }
    }
}