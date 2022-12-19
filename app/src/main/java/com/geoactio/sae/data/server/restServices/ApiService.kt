package com.geoactio.sae.data.server.restServices

import com.geoactio.sae.data.server.restServices.models.LineaResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("lineas")
    fun getLineas(): Call<ArrayList<LineaResponse>>

    @GET("trayectos")
    fun getTrayectos(@Query("id_linea") idLinea: String): Call<ArrayList<String>>
}
