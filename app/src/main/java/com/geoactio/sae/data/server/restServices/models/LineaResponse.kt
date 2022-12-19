package com.geoactio.sae.data.server.restServices.models

import com.google.gson.annotations.SerializedName

data class LineaResponse(
    @SerializedName("id")
    var id: String,
    @SerializedName("name")
    var nombre: String,
    @SerializedName("color")
    var color: String,
    @SerializedName("fontcolor")
    var fontColor: String,
    @SerializedName("tipo")
    var tipo: String,
    @SerializedName("pdf")
    var pdf: String
)
