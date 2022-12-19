package com.geoactio.sae.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geoactio.domain.Parada
import com.geoactio.sae.data.delegates.GetParadasCercanasDelegate
import com.geoactio.sae.data.server.SoapServices
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel(), GetParadasCercanasDelegate {
    sealed class UiModel {
        object Content: UiModel()
        data class ParadasFound(val paradas: List<Parada>): UiModel()
        object ErrorGettingParadas: UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) {
                _model.value = UiModel.Content
            }
            return _model
        }

    fun getParadasCercanas(position: LatLng) {
    }

    override fun succesGettingParadas(paradas: List<Parada>) {
        MainScope().launch {
            _model.value = UiModel.ParadasFound(paradas)
        }
    }

    override fun errorGettingParadas() {
        MainScope().launch {
            _model.value = UiModel.ErrorGettingParadas
        }
    }
}
