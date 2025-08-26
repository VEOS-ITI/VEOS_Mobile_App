package com.example.veos

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.veos.remote.RetrofitClient
import com.example.veos.remote.VehicleRemoteDataSource
import com.example.veos.remote.VehicleRemoteDataSourceImpl
import kotlinx.coroutines.launch


class homeFragment : Fragment() {

    private lateinit var remoteDataSource: VehicleRemoteDataSource
    private lateinit var drivingText: TextView
    private lateinit var batteryPercentage: TextView
    private lateinit var speedVal: TextView
    private lateinit var tripDuration: TextView
    private lateinit var rootLayout: View
    private lateinit var carImage: ImageView
    private lateinit var batteryIcon: ImageView
    private lateinit var batteryModeText: TextView
    private var isBatterySaverOn = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        drivingText = view.findViewById(R.id.drivingText)
        batteryPercentage = view.findViewById(R.id.batteryPercentage)
        speedVal = view.findViewById(R.id.speedValue)
        tripDuration = view.findViewById(R.id.tripDurationValue)
        batteryIcon = view.findViewById(R.id.batterSaverIcon)
        batteryModeText = view.findViewById(R.id.batteryMode)
        rootLayout = view.findViewById(R.id.rootLayout)      // ✅ fixed
        carImage = view.findViewById(R.id.carImage)
        // Init RemoteDataSourceImpl with ApiService
        remoteDataSource = VehicleRemoteDataSourceImpl(RetrofitClient.apiService)

        // Fetch gear when fragment loads
        loadGearStatus()
        loadBatteryPercentage()
        setupBatterySaverToggle()
        loadSpeed()
        loadTripDuration()

        return view
    }


    private fun loadGearStatus() {
        lifecycleScope.launch {
            try {
                val gear = remoteDataSource.getGearSelection()
                Log.i("Gear", "Gear = ${gear}")

                var chargeStatus: Int? = null
                try {
                    chargeStatus = remoteDataSource.getChargingState()
                    Log.i("charge", "charge = ${chargeStatus}")

                } catch (e: Exception) {
                    Log.e("Gear", "Charging state error: ${e.message}")
                }

                if (chargeStatus == 1) {
                    drivingText.text = "Charging"
                    rootLayout.setBackgroundResource(R.drawable.bgcharging)
                    carImage.setImageResource(R.drawable.chargingcar)

                } else if (gear == 0) {
                    drivingText.text = "Parking"
                    rootLayout.setBackgroundResource(R.drawable.bgparking)

                } else if (gear == 2) {
                    drivingText.text = "Driving"
                }

            } catch (e: Exception) {
                Log.e("Gear", "General error: ${e.message}", e)
                drivingText.text = "Error Fetching Status"
            }
        }
    }



    private fun loadBatteryPercentage() {
        lifecycleScope.launch {
            try {
                val battery = remoteDataSource.getBatteryLevel() // already Int

                // Show text
                batteryPercentage.text = "$battery%"

                // Change color if low
                if (battery <= 20) {
                    batteryPercentage.setTextColor(Color.RED)
                } else {
                    batteryPercentage.setTextColor(Color.WHITE)
                }

            } catch (e: Exception) {
                Log.e("Gear", "Error: ${e.message}", e)
                drivingText.text = "Error Fetching Status"
            }
        }
    }

    private fun loadSpeed() {
        lifecycleScope.launch {
            try {
                val speed = remoteDataSource.getVehicleSpeed()
                speedVal.text = "$speed Km/h"
            } catch (e : Exception) {
                speedVal.text = "error"
            }
        }
    }
    private fun loadTripDuration() {
        lifecycleScope.launch {
            try {
                val duration = remoteDataSource.getTripDuration()
                tripDuration.text = "$duration Km"

            } catch (e : Exception) {

            }
        }
    }

    private fun setupBatterySaverToggle() {
        batteryIcon.setOnClickListener {
            isBatterySaverOn = !isBatterySaverOn  // Toggle state

            if (isBatterySaverOn) {
                batteryIcon.setImageResource(R.drawable.batterysaverbuttonactive) // your "on" icon
                batteryModeText.text = "Battery Saving Mode: On"
            } else {
                batteryIcon.setImageResource(R.drawable.batterysaverbutton) // your "off" icon
                batteryModeText.text = "Battery Saving Mode: Off"
            }
        }
    }


}