package com.example.location_tracker.domain.mapper

import com.example.location_tracker.data.local.entity.Visit
import com.example.location_tracker.domain.model.VisitModel

object VisitMapper {
    fun Visit.toDomain() = VisitModel(
        id = id,
        locationId = locationId,
        locationName = locationName,
        entryTime = entryTime,
        exitTime = exitTime,
        duration = duration
    )

    fun VisitModel.toEntity() = Visit(
        id = id,
        locationId = locationId,
        locationName = locationName,
        entryTime = entryTime,
        exitTime = exitTime,
        duration = duration
    )
}