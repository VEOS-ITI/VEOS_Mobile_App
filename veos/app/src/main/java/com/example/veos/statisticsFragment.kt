package com.example.veos

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.veos.remote.RetrofitClient
import com.example.veos.remote.VehicleRemoteDataSource
import com.example.veos.remote.VehicleRemoteDataSourceImpl
import kotlinx.coroutines.launch

class statisticsFragment : Fragment() {

    private lateinit var remoteDataSource: VehicleRemoteDataSource

    // 🔹 Battery
    private lateinit var batteryPercentage: TextView
    private lateinit var batteryTemperature: TextView

    // 🔹 Speed
    private lateinit var currentSpeed: TextView
    private lateinit var limitSpeed: TextView
    private lateinit var maxSpeed: TextView

    // 🔹 Distance
    private lateinit var remainingDistance: TextView
    private lateinit var odometer: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_statistics, container, false)

        // Init RemoteDataSourceImpl
        remoteDataSource = VehicleRemoteDataSourceImpl(RetrofitClient.apiService)

        // 🔹 Bind views
        batteryPercentage = view.findViewById(R.id.battery_percentage)
        batteryTemperature = view.findViewById(R.id.batteryTemprature)

        currentSpeed = view.findViewById(R.id.current_speed)
        limitSpeed = view.findViewById(R.id.limit_speed)
        maxSpeed = view.findViewById(R.id.max_speed)

        remainingDistance = view.findViewById(R.id.remaining_distance)
        odometer = view.findViewById(R.id.odometer)

        // 🔹 Load Data
        loadBatteryInfo()
        loadSpeedInfo()
        loadDistanceInfo()

        return view
    }

    // ------------------- BATTERY -------------------
    private fun loadBatteryInfo() {
        lifecycleScope.launch {
            try {
                val battery = remoteDataSource.getBatteryLevel()
                val temperature = remoteDataSource.getBatteryTemp()

                // % battery
                batteryPercentage.text = "$battery%"
                if (battery <= 20) {
                    batteryPercentage.setTextColor(Color.RED)
                } else {
                    batteryPercentage.setTextColor(Color.WHITE)
                }

                // Temperature
                batteryTemperature.text = "$temperature °C"

            } catch (e: Exception) {
                batteryPercentage.text = "N/A"
                batteryTemperature.text = "N/A"
            }
        }
    }

    // ------------------- SPEED -------------------
    private fun loadSpeedInfo() {
        lifecycleScope.launch {
            try {
                val speed = remoteDataSource.getVehicleSpeed()
                val max = 280
                // val limit = remoteDataSource.getSpeedLimit()

                currentSpeed.text = "$speed km/h"
                maxSpeed.text = "$max km/h"
                // limitSpeed.text = "Limit: $limit km/h"

            } catch (e: Exception) {
                currentSpeed.text = "N/A"
                maxSpeed.text = "N/A"
                // limitSpeed.text = "N/A"
            }
        }
    }

    // ------------------- DISTANCE -------------------
    private fun loadDistanceInfo() {
        lifecycleScope.launch {
            try {
                // Calculate remaining distance
                val battery = remoteDataSource.getBatteryLevel()
                val remaining = (battery * 400) / 100
                val mileage = remoteDataSource.getOdometer()

                remainingDistance.text = "$remaining km"
                odometer.text = "$mileage km"

            } catch (e: Exception) {
                remainingDistance.text = "N/A"
                odometer.text = "N/A"
            }
        }
    }


}