package com.call.detector.view.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.call.detector.R
import com.call.detector.databinding.ActivityMainBinding
import com.call.detector.view.fragment.AllAppsFragment
import com.call.detector.view.fragment.BackgroundAppsFragment
import com.call.detector.view.fragment.InstalledAppsFragment
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val allAppsFragment = AllAppsFragment()
        val installedAppsFragment = InstalledAppsFragment()
        val backgroundAppsFragment = BackgroundAppsFragment()

        setCurrentFragment(allAppsFragment)

        binding.bottomNavigationView.setOnTabSelectListener(object :
            AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                Log.d("bottom_bar", "Selected index: $newIndex, title: ${newTab.title}")

                when (newIndex) {
                    0 -> {
                        setCurrentFragment(allAppsFragment)
                    }

                    1 -> {
                        setCurrentFragment(installedAppsFragment)
                    }

                    else -> {
                        setCurrentFragment(backgroundAppsFragment)
                    }
                }
            }

            // An optional method that will be fired whenever an already selected tab has been selected again.
            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                Log.d("bottom_bar", "Reselected index: $index, title: ${tab.title}")
            }
        })


        checkAndRequestPermissions()

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_PHONE_STATE),
            100
        )

        // Request overlay permission
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivity(intent)
        }

    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

    private fun checkAndRequestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG
        )

        val missingPermissions = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (missingPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, missingPermissions.toTypedArray(), 100)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            val deniedPermissions = permissions.filterIndexed { index, _ ->
                grantResults[index] != PackageManager.PERMISSION_GRANTED
            }
            if (deniedPermissions.isNotEmpty()) {
                // Handle permission denial
                Toast.makeText(this, "Permissions required for call detection", Toast.LENGTH_SHORT)
                    .show()
            } else {

                // Permissions granted
                Log.e(TAG, "MainActivity.onRequestPermissionsResult = Permissions granted")
                //Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

