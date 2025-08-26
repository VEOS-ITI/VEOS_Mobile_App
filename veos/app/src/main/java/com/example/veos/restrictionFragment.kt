package com.example.veos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.veos.pojo.VehicleRestriction
import com.example.veos.remote.RetrofitClient
import com.example.veos.remote.VehicleRemoteDataSourceImpl
import java.time.LocalDateTime
import kotlinx.coroutines.launch
import com.google.type.DateTime
import java.time.ZoneId
import java.time.ZoneOffset

class restrictionFragment : Fragment() {

    private lateinit var speedSeekBar: SeekBar
    private lateinit var speedValue: TextView
    private lateinit var tempSeekBar: SeekBar
    private lateinit var tempValue: TextView

    private val remoteDataSource = VehicleRemoteDataSourceImpl(RetrofitClient.apiService)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_restriction, container, false)

        // Speed restriction
        speedSeekBar = view.findViewById(R.id.speedSeekBar)
        speedValue = view.findViewById(R.id.speedValue)

        // ✅ Ensure starting text reflects current progress
        speedValue.text = "${speedSeekBar.progress} km/h"

        speedSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                speedValue.text = "$progress km/h"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                postRestriction() // ✅ Always take current values
            }
        })

        // Cabin temperature restriction
        tempSeekBar = view.findViewById(R.id.cabinTempSeekBar)
        tempValue = view.findViewById(R.id.TempValue)

        // Set seekbar range: 18 → 30
        tempSeekBar.max = 30 - 18
        tempSeekBar.progress = 0
        tempValue.text = "18 °C"

        tempSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val temp = progress + 18
                tempValue.text = "$temp °C"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                postRestriction() // ✅ Always take current values
            }
        })

        return view
    }

    // ✅ No need for nullable params
    private fun postRestriction() {
        lifecycleScope.launch {
            try {
                val selectedSpeed = speedSeekBar.progress          // always from SeekBar
                val selectedTemp = tempSeekBar.progress + 18       // map 0→18 ... 12→30

                val restriction = VehicleRestriction(
                    vehicle_id = "asdasd",
                    datetime = 1522,
                    timestamp = 40,
                    veos_perf_vehicle_speed = selectedSpeed,
                    veos_trip_distance = 564,
                    veos_trip_duration = 45,
                    veos_ev_battery_level = 50,
                    veos_cabin_temp = selectedTemp
                )
                val response = remoteDataSource.postVehicleRestriction(restriction)
                Log.i("Restriction", "Posted successfully: $response")
            } catch (e: Exception) {
                Log.e("Restriction", "Failed to post restriction: ${e.message}", e)


                // Log the response body from the server if it's an HTTP error
                if (e is retrofit2.HttpException) {
                    val errorResponse = e.response()?.errorBody()?.string()
                    Log.e("Restriction", "Error response: $errorResponse")
                    Log.e("Restriction", "HTTP error: ${e.code()}")
                }
            }
        }
    }
}
