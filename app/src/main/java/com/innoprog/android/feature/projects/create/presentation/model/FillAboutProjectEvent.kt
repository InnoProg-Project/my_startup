package com.innoprog.android.feature.projects.create.presentation.model

import android.net.Uri

sealed interface FillAboutProjectEvent {
    class PickPhoto(val uri: Uri) : FillAboutProjectEvent
    data object UnPinePhoto : FillAboutProjectEvent
}
