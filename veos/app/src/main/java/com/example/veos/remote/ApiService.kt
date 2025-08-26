package com.example.veos.remote

import com.example.veos.pojo.VehicleData
import com.example.veos.pojo.VehicleRestriction
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("telemetry/")
    suspend fun getVehicleData(): VehicleData

    // POST vehicle restrictions (or readings)
    @POST("vehicle-readings/")
    suspend fun postVehicleRestriction(
        @Body restriction: VehicleRestriction
    ): VehicleRestriction

}
