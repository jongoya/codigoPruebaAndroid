package com.geoactio.domain

import java.io.Serializable

data class Parada(var idParada: String = "", var nombre: String = "", var longitud: String = "", var latitud: String = "",
                  var distancia: Double = 0.0, var altitud: Float = 0F, var fechaIncidencia: String = "", var mensajeIncidencia: String = "",
                  var correspondencias: ArrayList<String> = arrayListOf(), var filtros: ArrayList<String> = arrayListOf(), var color: String = ""): Serializable
