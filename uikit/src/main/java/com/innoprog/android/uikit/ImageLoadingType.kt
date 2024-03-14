package com.innoprog.android.uikit

import androidx.annotation.DrawableRes

sealed class ImageLoadingType {
    data class ImageNetwork(
        val imageUrl: String,
        val backgroundColor: Int? = null,
        @DrawableRes val placeholderResId: Int? = null
    ) : ImageLoadingType()
    data class ImageDrawable(
        @DrawableRes
        val drawableResId: Int,
        val backgroundColor: Int? = null
    ) : ImageLoadingType()
}
