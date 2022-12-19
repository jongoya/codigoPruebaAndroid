package com.geoactio.sae.data.server.restServices.models

import com.google.gson.annotations.SerializedName

data class ParadaResponse(
    @SerializedName("id")
    var idParada: String = "",
    @SerializedName("name")
    var nombre: String = "",
    @SerializedName("latitude")
    var latitud: String = "",
    @SerializedName("longitude")
    var longitud: String = "",
    @SerializedName("fechaincidencia")
    var fechaIncidencia: String = "",
    @SerializedName("msgincidencia")
    var mensajeIncidencia: String = "",
    @SerializedName("corres")
    var correspondencias: ArrayList<String> = arrayListOf()

)
