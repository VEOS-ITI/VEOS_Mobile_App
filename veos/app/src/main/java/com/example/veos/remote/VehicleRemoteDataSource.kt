package com.example.veos.remote

import com.example.veos.pojo.VehicleData
import com.example.veos.pojo.VehicleRestriction

interface VehicleRemoteDataSource {
    suspend fun getVehicleData(): VehicleData

    suspend fun postVehicleRestriction(restriction: VehicleRestriction): VehicleRestriction

    suspend fun getId(): Int
    suspend fun getVehicleId(): Int
    suspend fun getTimestamp(): Int
    suspend fun getGearSelection(): Int
    suspend fun getVehicleSpeed(): Int
    suspend fun getBatteryLevel(): Int
    suspend fun getBatteryTemp(): Int
    suspend fun getChargingState(): Int
    suspend fun getChargeTimeRemaining(): Int
    suspend fun getOdometer(): Int
    suspend fun getTripDistance(): Int
    suspend fun getTripDuration(): Int
    suspend fun getHeadlightsState(): Boolean
    suspend fun getHighBeamState(): Boolean
    suspend fun getLowBatteryWarning(): Boolean
    suspend fun getBrakeSystemWarning(): Boolean
    suspend fun getVehicleReadyState(): Boolean
    suspend fun getLastUpdateTimestamp(): Int
    suspend fun getLatitude(): Double
    suspend fun getLongitude(): Double
}
