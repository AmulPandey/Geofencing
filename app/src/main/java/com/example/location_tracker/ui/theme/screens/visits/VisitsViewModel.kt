package com.example.location_tracker.ui.theme.screens.visits

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.location_tracker.data.local.entity.Visit
import com.example.location_tracker.domain.usecase.AddVisitUseCase
import com.example.location_tracker.domain.usecase.GetLocationByIdUseCase
import com.example.location_tracker.domain.usecase.GetLocationsUseCase
import com.example.location_tracker.domain.usecase.GetVisitsForLocationUseCase
import com.example.location_tracker.domain.usecase.GetVisitsUseCase
import com.example.location_tracker.domain.usecase.UpdateVisitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VisitsViewModel @Inject constructor(
    private val getVisitsUseCase: GetVisitsUseCase,
    private val addVisitUseCase: AddVisitUseCase,
    private val updateVisitUseCase: UpdateVisitUseCase,
    private val  getLocationByIdUseCase: GetLocationByIdUseCase,
    private val getVisitsForLocationUseCase: GetVisitsForLocationUseCase
) : ViewModel() {
    private val _visits = MutableStateFlow<List<Visit>>(emptyList())
    val visits: StateFlow<List<Visit>> get() = _visits

    init {
        observeVisits()
    }

    private fun observeVisits() {
        viewModelScope.launch(Dispatchers.IO) {
            getVisitsUseCase().collectLatest { visitList ->
                _visits.value = visitList
            }
        }
    }

    fun addVisitForStaticLocation(locationId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val location = getLocationByIdUseCase(locationId).firstOrNull()
                if (location != null) {
                    val existingVisits = getVisitsForLocationUseCase(locationId).firstOrNull()
                    val lastVisit = existingVisits?.lastOrNull()

                    if (lastVisit != null) {
                        if (lastVisit.exitTime == null) {
                            Log.d("VisitsViewModel", "Active visit already exists for location ID: $locationId, skipping new visit.")
                        } else {
                            val newVisit = Visit(
                                locationId = location.id,
                                locationName = location.name,
                                entryTime = System.currentTimeMillis(),
                                exitTime = lastVisit.exitTime,
                                duration = lastVisit.duration
                            )
                            addVisitUseCase(newVisit)
                            Log.d("VisitsViewModel", "New visit added for location ID: $locationId")
                        }
                    } else {

                        val newVisit = Visit(
                            locationId = location.id,
                            locationName = location.name,
                            entryTime = System.currentTimeMillis(),
                            exitTime = lastVisit?.exitTime,
                            duration = lastVisit?.duration
                        )
                        addVisitUseCase(newVisit)
                        Log.d("VisitsViewModel", "First visit added for location ID: $locationId")
                    }
                } else {
                    Log.e("VisitsViewModel", "Location not found for ID: $locationId")
                }
            } catch (e: Exception) {
                Log.e("VisitsViewModel", "Error adding visit: ${e.message}")
            }
        }
    }


}