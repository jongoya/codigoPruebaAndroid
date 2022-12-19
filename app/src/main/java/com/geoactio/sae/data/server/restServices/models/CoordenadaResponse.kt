package com.geoactio.sae.data.server.restServices.models

import com.google.gson.annotations.SerializedName

data class CoordenadaResponse(
    @SerializedName("Latitude")
    var latitud: String = "",
    @SerializedName("Longitude")
    var longitud: String
)
