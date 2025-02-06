package com.example.location_tracker.domain.mapper

import com.example.location_tracker.data.local.entity.Location
import com.example.location_tracker.domain.model.LocationModel

object LocationMapper {
    fun Location.toDomain() = LocationModel(
        id = id,
        name = name,
        latitude = latitude,
        longitude = longitude,
        radius = radius,
        createdAt = createdAt
    )

    fun LocationModel.toEntity() = Location(
        id = id,
        name = name,
        latitude = latitude,
        longitude = longitude,
        radius = radius,
        createdAt = createdAt
    )
}