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
import com.example.location_tracker.ui.theme.common.VisitItem
import com.example.location_tracker.ui.theme.screens.locations.LocationWithVisits
import com.example.location_tracker.ui.theme.screens.locations.LocationsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(
    locationId: Long,
    navController: NavController,
    viewModel: LocationsViewModel = hiltViewModel()
) {
    val locationWithVisits by viewModel.locationWithVisits.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(locationId) {
        viewModel.loadLocation(locationId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(locationWithVisits?.location?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            if (isLoading) {
                CircularProgressIndicator()
            }

            error?.let {
                Text(text = it, color = Color.Red)
            }

            locationWithVisits?.let { data ->
                LocationDetailsContent(data)
            }
        }
    }
}



@Composable
fun LocationDetailsContent(locationWithVisits: LocationWithVisits) {
    Column {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = locationWithVisits.location.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Latitude: ${locationWithVisits.location.latitude}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Longitude: ${locationWithVisits.location.longitude}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }


        LazyColumn {
            items(locationWithVisits.visits) { visit ->
                VisitItem(visit = visit)
            }
        }
    }
}