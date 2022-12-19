package com.geoactio.sae.fragments.home

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.geoactio.domain.Parada
import com.geoactio.sae.Activities.MainActivity
import com.geoactio.sae.Activities.delegates.LocationDelegate
import com.geoactio.sae.R
import com.geoactio.sae.common.GeoLocation
import com.geoactio.sae.data.delegates.GetParadasCercanasDelegate
import com.geoactio.sae.data.server.SoapServices
import com.geoactio.sae.databinding.HomeFragmentBinding
import com.geoactio.sae.fragments.home.HomeViewModel.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor(): Fragment(), OnMapReadyCallback, LocationDelegate {
    lateinit var binding: HomeFragmentBinding
    private val viewModel: HomeViewModel by viewModels()
    private var saveState: Bundle? = null
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = HomeFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUI))
        saveState = savedInstanceState
        (requireActivity() as MainActivity).hideInfoButton()
    }

    private fun updateUI(model: UiModel) {
        when (model) {
            UiModel.Content -> {
                initializeMap()
            }
            UiModel.ErrorGettingParadas -> {

            }
            is UiModel.ParadasFound -> {
               model.paradas.forEach {
                    googleMap.addMarker(MarkerOptions().position(LatLng(it.latitud.toDouble(), it.longitud.toDouble())).title(it.nombre).icon(
                        BitmapDescriptorFactory.fromResource(R.drawable.bus_marker)
                    ))
                }
            }
        }
    }

    private fun initializeMap() {
        binding.mapView.onCreate(saveState)
        binding.mapView.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        this.googleMap = p0
        (requireActivity() as MainActivity).checkLocationPermission(this)
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun successLocationRequest() {
        GeoLocation(requireActivity(), this)
    }

    override fun lastLocationFound(location: Location) {
    }
}
