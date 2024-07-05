package com.innoprog.android.feature.newsrecycleview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.databinding.ItemNewsBinding
import com.innoprog.android.feature.feed.newsfeed.domain.models.NewsWithProject

class NewsAdapter(
    var newsList: MutableList<NewsWithProject>,
    private val onNewsClick: (NewsWithProject) -> Unit
) : RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = NewsViewHolder(binding)
        holder.itemView.setOnClickListener {
            onNewsClick.invoke(newsList[holder.bindingAdapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount() = newsList.size
}
