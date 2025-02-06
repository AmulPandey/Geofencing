# Location Tracker

**Location Tracker** is an Android application that helps you track and manage geofences, monitor locations, and capture entry/exit times for specific locations. The app uses **Geofencing** technology to track when users enter or exit predefined locations such as home, office, etc.

## Features

- **Track Locations**: You can define static locations (e.g., Home, Office) with geofencing to monitor when a user enters or exits.
- **Real-time Location Tracking**: Tracks the user's current location and updates in real-time.
- **Entry/Exit Time**: Displays the time when a user enters or exits a geofenced location.
- **Location History**: View the history of your visits to specific locations.
- **Geofencing**: Integrates Google Geofencing API to trigger actions when entering or exiting geofences.
- **Dynamic Location Updates**: Allows adding new locations and dynamically updating geofences.

## Tech Stack

- **Android**: Kotlin, Jetpack Compose
- **Geofencing API**: Google's Location API
- **Google Maps**: For location visualization
- **Hilt**: For dependency injection
- **Room Database**: For local storage of visit data
- **Navigation**: Jetpack Navigation Component for in-app navigation
- **Location Tracking**: GPS, Fused Location Provider API

## Prerequisites

Before running this project, make sure you have:

- **Android Studio** installed.
- **Firebase project** set up.
- **Google Maps API key**.
- Necessary permissions to access the device's location.

## Setup

1. **Clone the repository**:

   ```bash
   git clone https://github.com/AmulPandey/Geofencing.git
