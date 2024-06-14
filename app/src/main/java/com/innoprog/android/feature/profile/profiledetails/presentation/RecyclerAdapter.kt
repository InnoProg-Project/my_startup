package com.innoprog.android.feature.profile.profiledetails.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.databinding.ItemNewsBinding
import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWrapper

class RecyclerAdapter(
    var publicationList: ArrayList<FeedWrapper>,
    private val onNewsClick: (FeedWrapper) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemNewsBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(publicationList[position])
        holder.itemView.setOnClickListener {
            onNewsClick.invoke(publicationList[position])
        }
    }

    override fun getItemCount(): Int {
        return publicationList.size
    }
}
