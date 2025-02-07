package com.example.location_tracker.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.location_tracker.R
import com.example.location_tracker.data.local.entity.Visit
import com.example.location_tracker.data.repository.LocationRepository
import com.example.location_tracker.data.repository.VisitRepository
import com.example.location_tracker.utils.Constants
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GeofencingBroadcastReceiver : BroadcastReceiver() {
    @Inject
    lateinit var visitRepository: VisitRepository

    @Inject
    lateinit var locationRepository: LocationRepository

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Constants.ACTION_GEOFENCE_EVENT) return

        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        val triggeringGeofences = geofencingEvent?.triggeringGeofences ?: return

        if (geofencingEvent != null) {
            if (geofencingEvent.hasError()) {
                Log.e("GeofencingReceiver", "Error code: ${geofencingEvent.errorCode}")
                return
            }

            when (geofencingEvent.geofenceTransition) {
                Geofence.GEOFENCE_TRANSITION_ENTER -> handleEnter(geofencingEvent, context)
                Geofence.GEOFENCE_TRANSITION_EXIT -> handleExit(geofencingEvent)
            }
        }

    }

    private fun handleEnter(event: GeofencingEvent, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            event.triggeringGeofences?.forEach { geofence ->
                val locationId = geofence.requestId.toLong()
                val location = locationRepository.getLocationById(locationId)

                location?.let {
                    val visit = Visit(
                        locationId = locationId,
                        locationName = it.name,
                        entryTime = System.currentTimeMillis()
                    )
                    visitRepository.addVisit(visit)
                    showNotification(visit, context)
                }
            }
        }
    }

    private fun handleExit(event: GeofencingEvent) {
        CoroutineScope(Dispatchers.IO).launch {
            val activeVisits = visitRepository.getActiveVisits()
            val exitTime = System.currentTimeMillis()

            activeVisits.forEach { visit ->
                if (visit.exitTime == null) {
                    val duration = exitTime - visit.entryTime
                    val updatedVisit = visit.copy(
                        exitTime = exitTime,
                        duration = duration
                    )
                    visitRepository.updateVisit(updatedVisit)
                }
            }
        }
    }


    private fun showNotification(visit: Visit, context: Context) {
        val channelId = "geofencing_channel"

        val notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Geofencing",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Location Entry")
            .setContentText("Entered ${visit.locationName}")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(visit.id.toInt(), notification)
    }
}