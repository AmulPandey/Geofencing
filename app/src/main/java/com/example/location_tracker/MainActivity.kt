package com.example.location_tracker

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.location_tracker.data.repository.LocationRepository
import com.example.location_tracker.service.GeofencingService
import com.example.location_tracker.ui.theme.navigation.AppNavigation
import com.example.location_tracker.ui.theme.theme.Location_TrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var locationRepository: LocationRepository

    private val requiredPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) {
            initializeAndStartGeofencing()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            checkAndRequestPermissions()
        }

        lifecycleScope.launch {
            locationRepository.initializeLocationsIfEmpty()
            Log.d("MainActivity", "Locations initialized")
        }


        setContent {
            Location_TrackerTheme {
                AppNavigation()
            }
        }
    }

    private fun checkAndRequestPermissions() {
        val hasPermissions = requiredPermissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }

        if (!hasPermissions) {
            requestPermissionLauncher.launch(requiredPermissions)
        } else {
            initializeAndStartGeofencing()
        }
    }

    private fun initializeAndStartGeofencing() {
        lifecycleScope.launch {
            locationRepository.initializeLocationsIfEmpty()
            startGeofencingService()
        }
    }

    private fun startGeofencingService() {
        val intent = Intent(this, GeofencingService::class.java)
        startForegroundService(intent)
    }
}
