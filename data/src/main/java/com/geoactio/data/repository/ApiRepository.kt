package com.geoactio.data.repository

import com.geoactio.data.source.ApiDataSource

class ApiRepository(private val apiDataSource: ApiDataSource) {
    suspend fun getLineas() = apiDataSource.getLineas()
    suspend fun getTrayectos(idLinea: String) = apiDataSource.getTrayectos(idLinea)
}
