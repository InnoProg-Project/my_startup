package com.innoprog.android.uikit

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.google.android.material.imageview.ShapeableImageView
import com.innoprog.android.uikit.ext.applyStyleable

class InnoProgAvatarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val imageView by lazy { findViewById<ShapeableImageView>(R.id.avatar_image) }

    init {
        inflate(context, R.layout.innno_prog_avatar_view, this)
        attrs?.applyStyleable(context, R.styleable.AvatarCustomVIew) {

            val color = getColor(
                R.styleable.AvatarCustomVIew_avatarBackgroundColor,
                context.getColor(R.color.background_secondary)
            )
            this@InnoProgAvatarView.setBackgroundResource(R.drawable.circle)
            this@InnoProgAvatarView.background.setTint(color)
            imageView.setImageDrawable(getDrawable(R.styleable.AvatarCustomVIew_avatarImageReference))
        }
    }

    fun setNonEditableAvatar(avatar: Drawable?) {
        this@InnoProgAvatarView.background.setTint(context.getColor(R.color.background_secondary))
        imageView.setImageDrawable(avatar)
        imageView.isClickable = false
    }

    fun setNonEditablePlaceholder() {
        this@InnoProgAvatarView.background.setTint(context.getColor(R.color.background_secondary))
        imageView.setImageDrawable(getDrawable(context, R.drawable.ic_person))
        imageView.isClickable = false
    }

    fun setEditableAvatar(avatar: Drawable) {
        this@InnoProgAvatarView.background.setTint(context.getColor(R.color.text_secondary))
        imageView.setImageDrawable(avatar)
        imageView.isClickable = true
    }

    fun setEditablePlaceholder() {
        this@InnoProgAvatarView.background.setTint(context.getColor(R.color.text_secondary))
        imageView.setImageDrawable(getDrawable(context, R.drawable.ic_camera))
        imageView.isClickable = true
    }
}
