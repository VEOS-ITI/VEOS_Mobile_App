package com.example.veos

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class splashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Delay for 2 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPref = getSharedPreferences("app_prefs", MODE_PRIVATE)
            val isFirstLaunch = sharedPref.getBoolean("isFirstLaunch", true)

            if (isFirstLaunch) {
                // First launch → go to FirstLaunchingActivity
                startActivity(Intent(this, firstLaunchingActivity::class.java))
                sharedPref.edit().putBoolean("isFirstLaunch", false).apply()
            } else {
                // Not first time → go to MainActivity
                startActivity(Intent(this, MainActivity::class.java))
            }

            finish() // Close splash
        }, 2000) // 2 seconds
    }
}
