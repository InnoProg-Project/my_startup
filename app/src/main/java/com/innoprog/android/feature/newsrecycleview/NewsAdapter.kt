package com.innoprog.android.feature.newsrecycleview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.databinding.ItemNewsBinding
import com.innoprog.android.feature.feed.newsfeed.domain.models.NewsWithProject

class NewsAdapter(
    private val onNewsClick: (NewsWithProject) -> Unit
) : ListAdapter<NewsWithProject, NewsViewHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = NewsViewHolder(binding)
        holder.itemView.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onNewsClick.invoke(getItem(position))
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class NewsDiffCallback : DiffUtil.ItemCallback<NewsWithProject>() {
    override fun areItemsTheSame(oldItem: NewsWithProject, newItem: NewsWithProject): Boolean {
        return oldItem.news.id == newItem.news.id
    }

    override fun areContentsTheSame(oldItem: NewsWithProject, newItem: NewsWithProject): Boolean {
        return oldItem == newItem
    }
}