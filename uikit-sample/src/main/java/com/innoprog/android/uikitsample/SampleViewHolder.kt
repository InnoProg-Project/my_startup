package com.innoprog.android.uikitsample

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val name: TextView = itemView.findViewById<TextView>(R.id.view_name)

    fun bind(item: ViewSample) {
        name.text = item.name
    }
}
