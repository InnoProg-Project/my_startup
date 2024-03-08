package com.innoprog.adnroid.uikit

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StyleableRes
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.ColorUtils
import com.innoprog.android.uikit.R

class LikeCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val countOfLikesTextView by lazy { findViewById<TextView>(R.id.count_of_likes) }
    private val likeCardView by lazy { findViewById<CardView>(R.id.like_card_view) }
    private val likeIcon by lazy { findViewById<ImageView>(R.id.like_icon) }

    init {
        inflate(context, R.layout.like_custom_view, this)
        attrs?.applyStyleable(context, R.styleable.LikeCustomView) {
            if (getBoolean(R.styleable.LikeCustomView_liked, false)) {
                val redColor = context.getColor(R.color.dark)
                val backgroundColor = ColorUtils.setAlphaComponent(redColor, 12)
                likeIcon.setColorFilter(redColor)
                likeCardView.background.setTint(backgroundColor)
                countOfLikesTextView.setTextColor(redColor)
            } else {
                likeIcon.setColorFilter(context.getColor(R.color.text_secondary))
                countOfLikesTextView.setTextColor(context.getColor(R.color.text_secondary))
                likeCardView.background.setTint(context.getColor(R.color.background_secondary))
            }
            countOfLikesTextView.text = getString(R.styleable.LikeCustomView_likeCount)
        }
    }

    fun like() {
        val redColor = context.getColor(R.color.dark)
        val backgroundColor = ColorUtils.setAlphaComponent(redColor, 12)
        likeIcon.setColorFilter(redColor)
        likeCardView.background.setTint(backgroundColor)
        countOfLikesTextView.setTextColor(redColor)
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

inline fun AttributeSet.applyStyleable(
    context: Context,
    @StyleableRes styleableResId: IntArray,
    action: TypedArray.() -> Unit
) {

    val typedArray = context.obtainStyledAttributes(this, styleableResId)
    typedArray.action()
    typedArray.recycle()
}
