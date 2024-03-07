package com.innoprog.android.uikitsample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ViewAdapter(
    private val data: Array<ViewSample>,
    private val clickListener: (ViewSample) -> Unit
) :
    RecyclerView.Adapter<SampleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return SampleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            clickListener.invoke(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}