package com.geoactio.sae.data.server.xmlParser

import android.util.Xml
import com.geoactio.domain.Parada
import com.google.android.gms.maps.model.LatLng
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

object XmlParser {
    fun parseParadasCercanas(responseStream: InputStream, position: LatLng): List<Parada> {
        val parser = Xml.newPullParser()
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(responseStream, null)
        var evenType = parser.eventType
        val paradas: ArrayList<Parada> = arrayListOf()
        var parada: Parada? = null
        while (evenType != XmlPullParser.END_DOCUMENT) {
            if (evenType == XmlPullParser.START_TAG) {
                if (parser.name == "AnnotatedStopPointRef") {
                    parada = Parada()
                }

                if (parser.name == "StopPointRef") {
                    parada!!.idParada = parser.nextText()
                }

                if (parser.name == "StopName") {
                    parada!!.nombre = parser.nextText()
                }

                if (parser.name == "Latitude") {
                    parada!!.latitud = parser.nextText()
                }

                if (parser.name == "Longitude") {
                    parada!!.longitud = parser.nextText()
                }

                if (parser.name == "LineRef") {
                    val correspondencia = parser.nextText()
                    if (!parada!!.correspondencias.contains(correspondencia)) {
                        parada.correspondencias.add(correspondencia)
                    }
                }
            }

            if (evenType == XmlPullParser.END_TAG) {
                if (parser.name == "AnnotatedStopPointRef") {
                    val distancia = distanceInKm(position.latitude, position.longitude, parada!!.latitud.toDouble(), parada.longitud.toDouble())
                    parada.distancia = distancia
                    paradas.add(parada)
                }
            }

            evenType = parser.next()
        }

        return paradas.sortedWith(compareBy { it.distancia })
    }

    private fun distanceInKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = sin(deg2rad(lat1)) * sin(deg2rad(lat2)) + cos(deg2rad(lat1)) * cos(deg2rad(lat2)) * cos(deg2rad(theta))
        dist = acos(dist)
        dist = rad2deg(dist)
        dist *= 60 * 1.1515
        dist *= 1.609344
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }
}
