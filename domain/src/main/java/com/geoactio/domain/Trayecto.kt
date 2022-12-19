package com.geoactio.domain

import java.io.Serializable

data class Trayecto(
    var id: String = "",
    var nombre: String = "",
    var idLinea: String = "",
    var coordenadas: ArrayList<Coordenada> = arrayListOf(),
    var paradas: ArrayList<Parada> = arrayListOf(),
    var inicio: String = "",
    var fin: String = ""
): Serializable
