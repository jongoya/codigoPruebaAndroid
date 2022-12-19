package com.geoactio.sae.data

import com.geoactio.domain.Linea
import com.geoactio.sae.data.server.restServices.models.LineaResponse

fun ArrayList<LineaResponse>.toDomainArray() = map { Linea(it.id, it.nombre, it.color, it.fontColor, it.tipo, "", it.pdf) }
