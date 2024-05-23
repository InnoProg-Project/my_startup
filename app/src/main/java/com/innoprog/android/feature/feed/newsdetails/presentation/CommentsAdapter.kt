package com.innoprog.android.feature.feed.newsdetails.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.databinding.ItemCommentBinding
import com.innoprog.android.feature.feed.newsdetails.domain.models.CommentModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class CommentsAdapter(
    private val commentsList: List<CommentModel>,
    private val onCommentClickListener: CommentsAdapter.OnClickListener
) : RecyclerView.Adapter<CommentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CommentsViewHolder(ItemCommentBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.bind(commentsList[position], onCommentClickListener, holder.itemView.context)
    }

    override fun getItemCount(): Int {
        return commentsList.size
    }

    interface OnClickListener {
        fun onItemClick(position: Int, comment: CommentModel, context: Context)
    }
}

class CommentsViewHolder(private val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(comment: CommentModel, onCommentClickListener: CommentsAdapter.OnClickListener, context: Context) {
        binding.apply {
            tvCommentAuthorName.text = comment.commentAuthor
            tvCommentContent.text = comment.commentContent

            itemView.setOnClickListener {
                onCommentClickListener.onItemClick(absoluteAdapterPosition, comment, context)
            }

            tvDeleteComment.setOnClickListener {
                Toast.makeText(context, "Удаляем комментарий", Toast.LENGTH_SHORT).show()
            }

            val inputDate = comment.commentCreationDate
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormatter = DateTimeFormatter.ofPattern("d MMMM", Locale("ru"))
            val dateTime = LocalDateTime.parse(inputDate, inputFormatter)
            tvCommentDate.text = dateTime.format(outputFormatter)
        }
    }
}
