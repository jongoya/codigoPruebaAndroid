package com.geoactio.domain

import java.io.Serializable

data class Linea(
    var id: String,
    var nombre: String,
    var color: String,
    var fontColor: String,
    var tipo: String,
    var pdfHorarios: String,
    var pdf: String): Serializable
