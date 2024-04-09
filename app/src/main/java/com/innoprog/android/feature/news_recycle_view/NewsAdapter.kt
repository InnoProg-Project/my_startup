package com.innoprog.android.feature.news_recycle_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.databinding.ItemNewsBinding
import com.innoprog.android.feature.feed.news_feed.domain.models.News

class NewsAdapter(
    private val newsList: ArrayList<News>,
    //private val onNewsClick: (News) -> Unit
) : RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(ItemNewsBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
        holder.itemView.setOnClickListener {
            //onNewsClick.invoke(newsList[position])
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}
