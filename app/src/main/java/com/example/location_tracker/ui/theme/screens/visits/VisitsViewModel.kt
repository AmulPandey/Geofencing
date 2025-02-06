package com.example.location_tracker.ui.theme.screens.visits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.location_tracker.data.local.entity.Visit
import com.example.location_tracker.domain.usecase.GetVisitsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class VisitsViewModel @Inject constructor(
    getVisitsUseCase: GetVisitsUseCase
) : ViewModel() {
    val visits: StateFlow<List<Visit>> = getVisitsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}