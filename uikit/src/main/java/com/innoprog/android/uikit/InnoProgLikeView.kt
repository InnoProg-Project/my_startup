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
    private val likeIcon by lazy { findViewById<ImageView>(R.id.like_icon) }

    private var liked: Boolean = false
    private var likeCount: Int = 0

    init {
        inflate(context, R.layout.inno_prog_like_view, this)
        attrs?.applyStyleable(context, R.styleable.LikeCustomView) {

            setBackgroundResource(R.drawable.rectangle_with_corners)

            if (getBoolean(R.styleable.LikeCustomView_liked, false)) {

                likeIcon.setColorFilter(context.getColor(R.color.like_color))
                background.setTint(context.getColor(R.color.like_background_color))
                countOfLikesTextView.setTextColor(context.getColor(R.color.like_color))
                liked = true
            } else {

                likeIcon.setColorFilter(context.getColor(R.color.text_secondary))
                countOfLikesTextView.setTextColor(context.getColor(R.color.text_secondary))
                background.setTint(context.getColor(R.color.background_secondary))
                liked = false
            }

            likeCount = getInt(R.styleable.LikeCustomView_likeCount, 0)
            countOfLikesTextView.text = likeCount.toString()
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
        background.setTint(context.getColor(R.color.like_background_color))
        countOfLikesTextView.setTextColor(context.getColor(R.color.like_color))

        likeCount++
        countOfLikesTextView.text = "$likeCount"
        liked = true
    }

    private fun unlike() {
        likeIcon.setColorFilter(context.getColor(R.color.text_secondary))
        countOfLikesTextView.setTextColor(context.getColor(R.color.text_secondary))
        background.setTint(context.getColor(R.color.background_secondary))

        likeCount--
        countOfLikesTextView.text = "$likeCount"
        liked = false
    }

    fun setLikeCount(count: Int) {
        likeCount = count
        countOfLikesTextView.text = count.toString()
    }
}
