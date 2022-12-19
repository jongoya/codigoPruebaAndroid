package com.geoactio.data.source

interface ApiDataSource {
    suspend fun getLineas(): ServerResponse
    suspend fun getTrayectos(idLinea: String): ServerResponse
}
