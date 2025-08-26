package com.example.veos

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.veos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.apply {
            itemIconTintList = null   // disable tinting
            itemRippleColor = null    // disable ripple
            itemBackground = null
            itemTextColor = ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_checked),
                    intArrayOf(-android.R.attr.state_checked)
                ),
                intArrayOf(
                    getColor(android.R.color.white),
                    Color.parseColor("#8A3FD0")
                )
            )
        }

        // Default fragment = Home
        replaceFragment(homeFragment())
        setActiveIcon(R.id.home, R.drawable.ic_home_active)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(homeFragment())
                    setActiveIcon(R.id.home, R.drawable.ic_home_active)
                    true
                }
                R.id.statistics -> {
                    replaceFragment(statisticsFragment())
                    setActiveIcon(R.id.statistics, R.drawable.ic_statistics_active)
                    true
                }
                R.id.restriction -> {
                    replaceFragment(restrictionFragment())
                    setActiveIcon(R.id.restriction, R.drawable.ic_restrictions_active)
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

    private fun setActiveIcon(selectedId: Int, activeIcon: Int) {
        val menu = binding.bottomNavigationView.menu

        // Reset all icons first
        menu.findItem(R.id.home).setIcon(R.drawable.ic_home)
        menu.findItem(R.id.statistics).setIcon(R.drawable.ic_statistics)
        menu.findItem(R.id.restriction).setIcon(R.drawable.ic_restrictions)

        // Set active icon
        menu.findItem(selectedId).setIcon(activeIcon)
    }
}
