package com.example.a01_storyapp.peta

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.a01_storyapp.R
import com.example.a01_storyapp.databinding.ActivityPetaBinding
import com.example.a01_storyapp.kumpulan_view.masuk.MasukActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class PetaActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityPetaBinding
    private lateinit var userLocPreferences: SharedPreferences
    private lateinit var petaViewModel: PetaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPetaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setLogo(R.drawable.ic_back)
        supportActionBar?.setDisplayUseLogoEnabled(true)

        petaViewModel = ViewModelProvider(this, MapsViewModelFactory(this))[PetaViewModel::class.java]

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        userLocPreferences = getSharedPreferences(MasukActivity.PREFS_NAME, Context.MODE_PRIVATE)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mendapatkanMyLocation()
        mengaturMapStyle()

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        val token = userLocPreferences.getString(MasukActivity.TOKEN, "").toString()

        petaViewModel.mendapatkanLocation(token)

        petaViewModel.lastLoc.observe(this){
            for (location in it){
                if (location.lat != null && location.lon != null){
                    val position = LatLng(location.lat, location.lon)
                    mMap.addMarker(
                        MarkerOptions()
                            .position(position)
                            .title(location.name)
                            .snippet(location.description)
                    )
                }
            }
            val newLoc =
                LatLng(it[0].lat, it[0].lon)
            mMap.animateCamera((CameraUpdateFactory.newLatLngZoom(newLoc, 5f)))
        }
    }

    //mapStyle
    private fun mengaturMapStyle() {
        try {
            val succes =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.peta_style))
            if(!succes){
                Log.e(ContentValues.TAG, resources.getString(R.string.style))
            }
        }catch (exception: Resources.NotFoundException){
            Log.e(ContentValues.TAG, resources.getString(R.string.error_style), exception)
        }
    }

    //getMyLokasi
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ){isGranted : Boolean ->
            if (isGranted){
                mendapatkanMyLocation()
            }
        }

    private fun mendapatkanMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ){
            mMap.isMyLocationEnabled = true
        }else{
            requestPermissionLauncher.launch((android.Manifest.permission.ACCESS_FINE_LOCATION)
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}