package com.innoprog.android.feature.feed.newsdetails.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.databinding.ItemCommentBinding
import com.innoprog.android.feature.feed.newsdetails.domain.models.CommentModel

class CommentsAdapter(
    private val commentsList: List<CommentModel>,
    private val onCommentClick: (CommentModel) -> Unit,
    private val onDeleteClick: (CommentModel) -> Unit
) : RecyclerView.Adapter<CommentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentsViewHolder(binding, onCommentClick, onDeleteClick)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.bind(commentsList[position])
    }

    override fun getItemCount(): Int {
        return commentsList.size
    }

    fun updateItem(position: Int) {
        notifyItemChanged(position)
    }
}

class CommentsViewHolder(
    private val binding: ItemCommentBinding,
    private val onCommentClick: (CommentModel) -> Unit,
    private val onDeleteClick: (CommentModel) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(comment: CommentModel) {
        binding.apply {
            tvCommentAuthorName.text = comment.commentAuthor.name
            tvCommentContent.text = comment.commentContent

            tvCommentDate.text = getFormattedDate(comment.commentCreationDate)

            val context = binding.root.context
            if (comment.isClicked) {
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        com.innoprog.android.uikit.R.color.background_secondary
                    )
                )
                tvDeleteComment.isVisible = true
            } else {
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        com.innoprog.android.uikit.R.color.background_primary
                    )
                )
                tvDeleteComment.isVisible = false
            }

            root.setOnClickListener {
                onCommentClick.invoke(comment)
            }

            tvDeleteComment.setOnClickListener {
                onDeleteClick.invoke(comment)
            }
        }
    }
}
