package com.geoactio.usecases

import com.geoactio.data.repository.ApiRepository

class GetTrayectos(private val apiRepository: ApiRepository) {
    suspend fun invoke(idLinea: String) = apiRepository.getTrayectos(idLinea)
}
