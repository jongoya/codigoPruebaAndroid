package com.geoactio.data.source

data class ServerResponse(
    val data: Any,
    val success: Boolean,
    val api_error_message: String = "",
    val code: Int = -1
)
