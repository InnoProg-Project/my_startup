package com.innoprog.android.feature.profile.editingprofile.presentation

import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.profile.editingprofile.domain.ProfileUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditingProfileViewModel @Inject constructor(private val profileUseCase: ProfileUseCase) :
    BaseViewModel() {
    fun saveProfile() {
        viewModelScope.launch {
            try {
                val profileDto = profileUseCase.saveProfile()
                // Handle the retrieved profile data as needed
            } catch (e: Exception) {
                // Handle any exceptions or errors
            }
        }
    }
}
