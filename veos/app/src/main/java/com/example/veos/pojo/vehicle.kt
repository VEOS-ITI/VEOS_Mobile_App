package com.example.veos.pojo

data class VehicleData(
    val id: Int,
    val vehicle_id: Int,
    val timestamp: Int,
    val veos_gear_selection: Int,
    val veos_perf_vehicle_speed: Int,
    val veos_ev_battery_level: Int,
    val veos_ev_battery_temp: Int,
    val veos_ev_charging_state: Int, //
    val veos_ev_charge_time_remaining: Int,
    val veos_perf_odometer: Int,
    val veos_trip_distance: Int,
    val veos_trip_duration: Int,
    val veos_headlights_state: Boolean,
    val veos_high_beam_lights_state: Boolean,
    val veos_ev_low_battery_warning: Boolean,
    val veos_brake_system_warning: Boolean,
    val veos_vehicle_ready_state: Boolean,
    val veos_last_update_timestamp: Int,
    val latitude: Double,
    val longitude: Double
)
