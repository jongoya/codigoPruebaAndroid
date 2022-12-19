package com.geoactio.sae.Activities.delegates

import android.location.Location

interface LocationDelegate {
    fun successLocationRequest()
    fun lastLocationFound(location: Location)
}
