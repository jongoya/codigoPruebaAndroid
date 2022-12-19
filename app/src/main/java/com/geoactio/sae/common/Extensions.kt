package com.geoactio.sae.common

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.geoactio.sae.R

fun Int.convertDpToPixel(context: Context): Int {
    return (this * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}

fun Activity.showAlertMessage(mensaje: String) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(getString(R.string.app_name))
    builder.setMessage(mensaje)
    builder.setPositiveButton(getString(R.string.app_name)) { _, _ -> }
    builder.show()
}
