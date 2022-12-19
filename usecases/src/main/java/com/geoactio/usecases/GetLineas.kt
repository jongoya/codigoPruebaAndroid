package com.geoactio.usecases

import com.geoactio.data.repository.ApiRepository

class GetLineas(private val apiRepository: ApiRepository) {
    suspend fun invoke() = apiRepository.getLineas()
}
