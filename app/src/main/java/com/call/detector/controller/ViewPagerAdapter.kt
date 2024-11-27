package com.call.detector.controller

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.call.detector.view.fragment.AllAppsFragment
import com.call.detector.view.fragment.BackgroundAppsFragment
import com.call.detector.view.fragment.InstalledAppsFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllAppsFragment()
            1 -> InstalledAppsFragment()
            2 -> BackgroundAppsFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}

