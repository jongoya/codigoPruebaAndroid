package com.geoactio.sae.data.server

import com.geoactio.sae.data.delegates.GetParadasCercanasDelegate
import com.geoactio.sae.data.server.xmlParser.XmlParser
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

object SoapServices {
    val url: String = "http://80.32.129.44:8080/Siri/SiriWS.asmx"

    fun getParadasCercanas(position: LatLng, soapAction: String, delegate: GetParadasCercanasDelegate) {
        val client = OkHttpClient.Builder().build()
        val mediaType: MediaType = "text/xml".toMediaTypeOrNull()!!
        val soap = "<?xml version='1.0' encoding='utf-8'?><soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/' " +
                "xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema'><soap:Body>" +
                "<StopPointsDiscovery xmlns='http://tempuri.org/'><request><Request xmlns=''>" +
                "<RequestTimestamp xmlns='http://www.siri.org.uk/siri'>${generateTimeStamp()}</RequestTimestamp>" +
                "<AccountId xmlns='http://www.siri.org.uk/siri'>wssabadell</AccountId>" +
                "<AccountKey xmlns='http://www.siri.org.uk/siri'>WS.sabadell</AccountKey>" +
                "<Circle xmlns='http://www.siri.org.uk/siri'><Latitude>${position.latitude}</Latitude>" +
                "<Longitude>${position.longitude}</Longitude><Precision>300</Precision></Circle><LineRef xmlns='http://www.siri.org.uk/siri' />" +
                "<StopPointsDetailLevel xmlns='http://www.siri.org.uk/siri'>full</StopPointsDetailLevel></Request></request>" +
                "</StopPointsDiscovery></soap:Body></soap:Envelope>"
        val body: RequestBody = RequestBody.create(mediaType, soap)
        val request = Request.Builder().url(url).post(body).addHeader("Content-Type", "text/xml").addHeader("SOAPAction", soapAction).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                delegate.errorGettingParadas()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        delegate.errorGettingParadas()
                        return
                    }

                    val responseStream: InputStream = response.body!!.byteStream()
                    delegate.succesGettingParadas(XmlParser.parseParadasCercanas(responseStream, position))
                }
            }
        })
    }

    private fun generateTimeStamp() : String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ")
        return sdf.format(Date())
    }
}
