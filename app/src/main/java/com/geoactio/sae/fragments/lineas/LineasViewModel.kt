package com.geoactio.sae.fragments.lineas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geoactio.domain.Linea
import com.geoactio.usecases.GetLineas
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LineasViewModel @Inject constructor(private val getLineas: GetLineas): ViewModel() {
    sealed class UiModel {
        data class Content(val lineas: List<Linea>): UiModel()
        object ErrorLineas: UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) {
                getLineas()
            }
            return _model
        }

    fun getLineas() {
        viewModelScope.launch {
            val response = getLineas.invoke()
            if (response.success) {
                _model.value = UiModel.Content(response.data as List<Linea>)
            } else {
                _model.value = UiModel.ErrorLineas
            }
        }
    }
}
