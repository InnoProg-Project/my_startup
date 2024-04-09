package com.innoprog.android.feature.news_recycle_view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.databinding.ItemNewsBinding
import com.innoprog.android.feature.feed.news_feed.domain.models.News

class NewsAdapter(
    private val newsList: ArrayList<News>,
    private val onNewsClickListener: OnClickListener
) : RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(ItemNewsBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position], onNewsClickListener)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    interface OnClickListener {
        fun onItemClick(news: News)
    }
}
