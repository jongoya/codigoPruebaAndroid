package com.geoactio.sae.fragments.launchScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LaunchScreenViewModel @Inject constructor(): ViewModel() {
    sealed class UiModel {
        object Content: UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) {
                _model.value = UiModel.Content
            }
            return _model
        }
}
