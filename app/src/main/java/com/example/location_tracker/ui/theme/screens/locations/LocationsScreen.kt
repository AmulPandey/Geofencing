import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.location_tracker.data.local.entity.Location
import com.example.location_tracker.ui.theme.common.LocationItem
import com.example.location_tracker.ui.theme.common.VisitItem
import com.example.location_tracker.ui.theme.screens.locations.LocationWithVisits
import com.example.location_tracker.ui.theme.screens.locations.LocationsViewModel
import com.example.location_tracker.ui.theme.screens.visits.VisitsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(
    locationId: Long = -1,
    navController: NavController,
    viewModel: LocationsViewModel = hiltViewModel(),
    visitsViewModel: VisitsViewModel = hiltViewModel()
) {
    val locations by viewModel.locations.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Locations") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (locations.isEmpty()) {
            Text(
                text = "No locations found in database",
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding)
            ) {
                items(locations) { location ->
                    LocationItem(
                        location = location,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable {
                                visitsViewModel.addVisitForStaticLocation(location.id)
                                navController.navigate("visits/${location.id}")
                            }
                    )
                }
            }

        }
    }
}

@Composable
fun LocationItem(
    location: Location,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = location.name,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Latitude: ${location.latitude}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Longitude: ${location.longitude}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Radius: ${location.radius}m",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}