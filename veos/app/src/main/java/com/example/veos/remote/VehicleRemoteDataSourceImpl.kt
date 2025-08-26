package com.example.veos.remote

import com.example.veos.pojo.VehicleData
import com.example.veos.pojo.VehicleRestriction

class VehicleRemoteDataSourceImpl(
    private val apiService: ApiService
) : VehicleRemoteDataSource {

    override suspend fun getVehicleData(): VehicleData {
        return apiService.getVehicleData()
    }

    override suspend fun postVehicleRestriction(restriction: VehicleRestriction): VehicleRestriction {
        return apiService.postVehicleRestriction(restriction)
    }

    override suspend fun getId(): Int {
        return getVehicleData().id
    }

    override suspend fun getVehicleId(): Int {
        return getVehicleData().vehicle_id
    }

    override suspend fun getTimestamp(): Int {
        return getVehicleData().timestamp
    }

    override suspend fun getGearSelection(): Int {
        return getVehicleData().veos_gear_selection
    }

    override suspend fun getVehicleSpeed(): Int {
        return getVehicleData().veos_perf_vehicle_speed
    }

    override suspend fun getBatteryLevel(): Int {
        return getVehicleData().veos_ev_battery_level
    }

    override suspend fun getBatteryTemp(): Int {
        return getVehicleData().veos_ev_battery_temp
    }

    override suspend fun getChargingState(): Int {
        return getVehicleData().veos_ev_charging_state
    }

    override suspend fun getChargeTimeRemaining(): Int {
        return getVehicleData().veos_ev_charge_time_remaining
    }

    override suspend fun getOdometer(): Int {
        return getVehicleData().veos_perf_odometer
    }

    override suspend fun getTripDistance(): Int {
        return getVehicleData().veos_trip_distance
    }

    override suspend fun getTripDuration(): Int {
        return getVehicleData().veos_trip_duration
    }

    override suspend fun getHeadlightsState(): Boolean {
        return getVehicleData().veos_headlights_state
    }

    override suspend fun getHighBeamState(): Boolean {
        return getVehicleData().veos_high_beam_lights_state
    }

    override suspend fun getLowBatteryWarning(): Boolean {
        return getVehicleData().veos_ev_low_battery_warning
    }

    override suspend fun getBrakeSystemWarning(): Boolean {
        return getVehicleData().veos_brake_system_warning
    }

    override suspend fun getVehicleReadyState(): Boolean {
        return getVehicleData().veos_vehicle_ready_state
    }

    override suspend fun getLastUpdateTimestamp(): Int {
        return getVehicleData().veos_last_update_timestamp
    }

    override suspend fun getLatitude(): Double {
        return getVehicleData().latitude
    }

    override suspend fun getLongitude(): Double {
        return getVehicleData().longitude
    }
}