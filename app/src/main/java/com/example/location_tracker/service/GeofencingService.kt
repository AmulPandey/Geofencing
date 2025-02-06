package com.example.location_tracker.service

import android.app.Service
import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.location_tracker.data.local.entity.Location
import com.example.location_tracker.data.repository.LocationRepository
import com.example.location_tracker.data.repository.VisitRepository
import com.example.location_tracker.utils.Constants
import com.example.location_tracker.utils.PermissionUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GeofencingService : Service() {
    @Inject
    lateinit var locationRepository: LocationRepository

    @Inject
    lateinit var visitRepository: VisitRepository

    private lateinit var geofencingClient: GeofencingClient
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(this, GeofencingBroadcastReceiver::class.java)
        intent.action = Constants.ACTION_GEOFENCE_EVENT
        PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
    }

    override fun onCreate() {
        super.onCreate()
        geofencingClient = LocationServices.getGeofencingClient(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setupGeofences()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    private fun setupGeofences() {
        serviceScope.launch {
            locationRepository.getAllLocations().collect { locations ->
                locations.forEach { location ->
                    addGeofence(location)
                }
            }
        }
    }

    fun addGeofence(location: Location) {
        if (!PermissionUtils.hasLocationPermissions(this)) return

        val geofence = Geofence.Builder()
            .setRequestId(location.id.toString())
            .setCircularRegion(
                location.latitude,
                location.longitude,
                location.radius
            )
            .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_DURATION)
            .setTransitionTypes(
                Geofence.GEOFENCE_TRANSITION_ENTER or
                        Geofence.GEOFENCE_TRANSITION_EXIT
            )
            .build()

        val request = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        geofencingClient.addGeofences(request, geofencePendingIntent)
            .addOnSuccessListener {
                Log.d("GeofencingService", "Geofence added for ${location.name}")
            }
            .addOnFailureListener { e ->
                Log.e("GeofencingService", "Failed to add geofence for ${location.name}", e)
            }
    }

    fun removeGeofence(locationId: String) {
        geofencingClient.removeGeofences(listOf(locationId))
    }
}