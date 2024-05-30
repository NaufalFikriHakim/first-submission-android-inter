package com.dicoding.picodiploma.loginwithanimation.view.maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dicoding.picodiploma.loginwithanimation.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityMapsBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val viewModel by viewModels<MapsViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        viewModel.listReview.observe(this){
            it.forEach { data ->
                val latLng = data.lat?.let { it1 -> data.lo?.let {it2 -> LatLng(it1, it2)} }
                latLng?.let {
                    mMap.addMarker(
                        MarkerOptions()
                            .position(latLng)
                            .title(data.name)
                            .snippet(data.description)
                    )
                }}

        }

        // Add a marker in Sydney and move the camera
        val indonesia = LatLng(6.0, 106.0)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(indonesia))

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
    }
}