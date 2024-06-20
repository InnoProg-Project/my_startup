package com.innoprog.android.feature.profile.editingprofile.data.network

sealed interface Request {
    data object PutProfile : Request
    data object PutProfileCompany : Request
}
