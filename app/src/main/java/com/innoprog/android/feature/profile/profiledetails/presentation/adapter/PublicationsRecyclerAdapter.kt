package com.innoprog.android.feature.profile.profiledetails.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.databinding.ItemNewsBinding
import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWrapper

class PublicationsRecyclerAdapter(
    var publications: ArrayList<FeedWrapper>,
    private val onPublicationClick: (FeedWrapper) -> Unit
) : RecyclerView.Adapter<PublicationsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicationsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PublicationsViewHolder(ItemNewsBinding.inflate(layoutInflater, parent, false)).apply {
            itemView.setOnClickListener {
                onPublicationClick.invoke(publications[bindingAdapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: PublicationsViewHolder, position: Int) {
        holder.bind(publications[position])
    }

    override fun getItemCount(): Int = publications.size
}
