package com.innoprog.android.uikit

import android.graphics.drawable.Drawable

sealed class ImageLoadingType {
    data class ImageNetwork(val imageUrl : String) : ImageLoadingType()
    data class ImageDrawable(val drawable: Drawable): ImageLoadingType()
}