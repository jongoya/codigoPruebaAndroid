package com.geoactio.sae.data.delegates

import com.geoactio.domain.Parada

interface GetParadasCercanasDelegate {
    fun succesGettingParadas(paradas: List<Parada>)
    fun errorGettingParadas()
}
