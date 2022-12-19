package com.geoactio.sae.data.server.restServices.datasource

import com.geoactio.data.source.ApiDataSource
import com.geoactio.data.source.ServerResponse
import com.geoactio.sae.data.server.restServices.ApiService
import com.geoactio.sae.data.server.restServices.models.LineaResponse
import com.geoactio.sae.data.toDomainArray
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

class RestDataSource(private val apiService: ApiService): ApiDataSource {
    override suspend fun getLineas(): ServerResponse = suspendCancellableCoroutine { continuation ->
        apiService.getLineas().enqueue(object : Callback<ArrayList<LineaResponse>> {
            override fun onResponse(call: Call<ArrayList<LineaResponse>>, response: Response<ArrayList<LineaResponse>>) {
                when(response.code()) {
                    200 -> {
                        val responseBody: ArrayList<LineaResponse>? = response.body()
                        if (responseBody != null) {
                            continuation.resume(ServerResponse(responseBody.toDomainArray(), true))
                        }
                        else{
                            continuation.resume(ServerResponse("", false))
                        }
                    }
                    else -> {
                        continuation.resume(ServerResponse("", false))
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<LineaResponse>>, t: Throwable) {
                continuation.resume(ServerResponse("", false))
            }
        })
    }

    override suspend fun getTrayectos(idLinea: String): ServerResponse = suspendCancellableCoroutine { continuation ->

    }
}
