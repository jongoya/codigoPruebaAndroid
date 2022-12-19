package com.geoactio.sae.Activities

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.geoactio.sae.Activities.MainActivityViewModel.*
import com.geoactio.sae.Activities.delegates.LocationDelegate
import com.geoactio.sae.R
import com.geoactio.sae.common.convertDpToPixel
import com.geoactio.sae.common.showAlertMessage
import com.geoactio.sae.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor(): AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var locationDelegate: LocationDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.uiModel.observe(this, Observer(::updateUI))
    }

    private fun updateUI(model: UiModel) {
        when (model) {
            is UiModel.Content -> {

            }
        }
    }

    fun hideNavBar() {
        val params = binding.naviBar.layoutParams
        params.height = 1.convertDpToPixel(applicationContext)
        binding.naviBar.layoutParams = params
    }

    fun showNavBar() {
        val params = binding.naviBar.layoutParams
        params.height = 50.convertDpToPixel(applicationContext)
        binding.naviBar.layoutParams = params
    }

    fun showBellButton() {
        binding.bellButton.visibility = View.VISIBLE
    }

    fun hideBellButton() {
        binding.bellButton.visibility = View.GONE
    }

    fun showInfoButton() {
        binding.infoButton.visibility = View.VISIBLE
    }

    fun hideInfoButton() {
        binding.infoButton.visibility = View.GONE
    }

    fun checkLocationPermission(delegate: LocationDelegate) {
        this.locationDelegate = delegate
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        } else {
            delegate.successLocationRequest()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED)) {
                        locationDelegate.successLocationRequest()
                    }
                } else {
                    showAlertMessage(getString(R.string.app_name))
                }
                return
            }
        }
    }
}
