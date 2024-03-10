package com.innoprog.android.uikit

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.innoprog.android.uikit.ext.applyStyleable

class LikeCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : CardView(context, attrs, defStyleAttr) {

    private val countOfLikesTextView by lazy { findViewById<TextView>(R.id.count_of_likes) }
    private val likeCardView by lazy { findViewById<CardView>(R.id.like_card_view) }
    private val likeIcon by lazy { findViewById<ImageView>(R.id.like_icon) }

    init {
        inflate(context, R.layout.like_custom_view, this)
        attrs?.applyStyleable(context, R.styleable.LikeCustomView) {

            if (getBoolean(R.styleable.LikeCustomView_liked, false)) {

                likeIcon.setColorFilter(context.getColor(R.color.like_color))
                likeCardView.background.setTint(context.getColor(R.color.like_background_color))
                countOfLikesTextView.setTextColor(context.getColor(R.color.like_color))
            } else {

                likeIcon.setColorFilter(context.getColor(R.color.text_secondary))
                countOfLikesTextView.setTextColor(context.getColor(R.color.text_secondary))
                likeCardView.background.setTint(context.getColor(R.color.background_secondary))
            }

            countOfLikesTextView.text = getString(R.styleable.LikeCustomView_likeCount)
        }
    }

    fun like() {
        likeIcon.setColorFilter(context.getColor(R.color.like_color))
        likeCardView.background.setTint(context.getColor(R.color.like_color))
        countOfLikesTextView.setTextColor(context.getColor(R.color.like_color))
        countOfLikesTextView.text =
            (Integer.parseInt(countOfLikesTextView.text.toString()) + 1).toString()
    }

    fun unlike() {
        likeIcon.setColorFilter(context.getColor(R.color.text_secondary))
        countOfLikesTextView.setTextColor(context.getColor(R.color.text_secondary))
        likeCardView.background.setTint(context.getColor(R.color.background_secondary))
        countOfLikesTextView.text =
            (Integer.parseInt(countOfLikesTextView.text.toString()) - 1).toString()
    }

}
