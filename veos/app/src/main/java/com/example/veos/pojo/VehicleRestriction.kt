package com.example.veos.pojo

import com.google.type.DateTime
import java.time.LocalDateTime

data class VehicleRestriction(
    val vehicle_id: String,
    val datetime: Long,
    val timestamp: Long,
    val veos_perf_vehicle_speed: Int,
    val veos_trip_distance: Int,
    val veos_trip_duration: Int,
    val veos_ev_battery_level: Int,
    val veos_cabin_temp: Int
)
