package com.call.detector.view.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.call.detector.R
import com.call.detector.databinding.ActivityMainBinding
import com.call.detector.view.fragment.AllAppsFragment
import com.call.detector.view.fragment.BackgroundAppsFragment
import com.call.detector.view.fragment.InstalledAppsFragment
import nl.joery.animatedbottombar.AnimatedBottomBar


@Suppress("UNUSED_EXPRESSION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

      /*  binding.bottomNavigationView.addBubbleListener {
            when (it) {
                R.id.AllApps -> setCurrentFragment(allAppsFragment)
                R.id.InstalledApps -> setCurrentFragment(installedAppsFragment)
                R.id.BackgroundApps -> setCurrentFragment(backgroundAppsFragment)

            }
            true
        }*/

        binding.bottomNavigationView.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
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
    }


    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}

