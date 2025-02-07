package com.example.location_tracker.ui.theme.screens.visits

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.location_tracker.ui.theme.common.VisitItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisitsScreen(
    navController: NavController,
    viewModel: VisitsViewModel = hiltViewModel(),
    onNavigateToLocations: () -> Unit
) {
    val visits by viewModel.visits.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recent Visits") },
                actions = {
                    IconButton(onClick = onNavigateToLocations) {
                        Icon(Icons.Default.LocationOn, "Locations")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(visits) { visit ->
                VisitItem(visit = visit)
            }
        }
    }
}

