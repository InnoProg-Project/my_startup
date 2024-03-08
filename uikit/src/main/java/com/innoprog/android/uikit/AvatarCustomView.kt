package com.innoprog.android.uikit

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.StyleableRes
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.imageview.ShapeableImageView

class AvatarCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val imageView by lazy { findViewById<ShapeableImageView>(R.id.avatar_image) }
    private val cardView by lazy { findViewById<FrameLayout>(R.id.avatar_frame_layout) }

    init {
        inflate(context, R.layout.avatar_custom_view, this)
        attrs?.applyStyleable(context, R.styleable.AvatarCustomVIew) {

            val color = getColor(
                R.styleable.AvatarCustomVIew_avatarBackgroundColor,
                context.getColor(R.color.background_secondary)
            )
            cardView.background.setTint(color)
            imageView.setImageDrawable(getDrawable(R.styleable.AvatarCustomVIew_avatarImageReference))
        }
    }

    fun setNonEditableAvatar(avatar: Drawable?) {
        cardView.background.setTint(context.getColor(R.color.background_secondary))
        imageView.setImageDrawable(avatar)
        imageView.isClickable = false
    }

    fun setNonEditablePlaceholder() {
        cardView.background.setTint(context.getColor(R.color.background_secondary))
        imageView.setImageDrawable(getDrawable(context, R.drawable.ic_person))
        imageView.isClickable = false
    }

    fun setEditableAvatar(avatar: Drawable) {
        cardView.background.setTint(context.getColor(R.color.text_secondary))
        imageView.setImageDrawable(avatar)
        imageView.isClickable = true
    }

    fun setEditablePlaceholder() {
        cardView.background.setTint(context.getColor(R.color.text_secondary))
        imageView.setImageDrawable(getDrawable(context, R.drawable.ic_camera))
        imageView.isClickable = true
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
