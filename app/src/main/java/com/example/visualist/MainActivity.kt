package com.example.visualist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fragmentContainer: FrameLayout

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_camera -> {
                    loadFragment(CameraFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_forum -> {
                    loadFragment(ForumFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_store -> {
                    loadFragment(StoreFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_home -> {
                    loadFragment(HomeFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        fragmentContainer = findViewById(R.id.fragment_container)

        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        // 默认加载第一个Fragment
        loadFragment(CameraFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
