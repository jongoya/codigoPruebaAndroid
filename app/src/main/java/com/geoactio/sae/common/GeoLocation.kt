package com.geoactio.sae.common
import android.annotation.SuppressLint
import android.app.Activity
import com.geoactio.sae.Activities.delegates.LocationDelegate
import com.google.android.gms.location.*


class GeoLocation @SuppressLint("MissingPermission") constructor(activity: Activity, delegate: LocationDelegate) {
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)

    init {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            if (it != null) {
                delegate.lastLocationFound(it)
            }
        }
    }
}
