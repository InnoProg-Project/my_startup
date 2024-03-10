package com.innoprog.android.uikit

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.innoprog.android.uikit.ext.applyStyleable

class InnoProgLikeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val countOfLikesTextView by lazy { findViewById<TextView>(R.id.count_of_likes) }
    private val likeFrameLayout by lazy { findViewById<FrameLayout>(R.id.like_card_view) }
    private val likeIcon by lazy { findViewById<ImageView>(R.id.like_icon) }

    private var liked: Boolean = false

    init {
        inflate(context, R.layout.inno_prog_like_view, this)
        attrs?.applyStyleable(context, R.styleable.LikeCustomView) {

            if (getBoolean(R.styleable.LikeCustomView_liked, false)) {

                likeIcon.setColorFilter(context.getColor(R.color.like_color))
                likeFrameLayout.background.setTint(context.getColor(R.color.like_background_color))
                countOfLikesTextView.setTextColor(context.getColor(R.color.like_color))
                liked = true
            } else {

                likeIcon.setColorFilter(context.getColor(R.color.text_secondary))
                countOfLikesTextView.setTextColor(context.getColor(R.color.text_secondary))
                likeFrameLayout.background.setTint(context.getColor(R.color.background_secondary))
                liked = false
            }

            countOfLikesTextView.text = getString(R.styleable.LikeCustomView_likeCount)
        }
    }

    fun click() {
        if (liked)
            unlike()
        else
            like()
    }

    private fun like() {
        likeIcon.setColorFilter(context.getColor(R.color.like_color))
        likeFrameLayout.background.setTint(context.getColor(R.color.like_background_color))
        countOfLikesTextView.setTextColor(context.getColor(R.color.like_color))
        countOfLikesTextView.text =
            (Integer.parseInt(countOfLikesTextView.text.toString()) + 1).toString()
        liked = true
    }

    private fun unlike() {
        likeIcon.setColorFilter(context.getColor(R.color.text_secondary))
        countOfLikesTextView.setTextColor(context.getColor(R.color.text_secondary))
        likeFrameLayout.background.setTint(context.getColor(R.color.background_secondary))
        countOfLikesTextView.text =
            (Integer.parseInt(countOfLikesTextView.text.toString()) - 1).toString()
        liked = false
    }
}
