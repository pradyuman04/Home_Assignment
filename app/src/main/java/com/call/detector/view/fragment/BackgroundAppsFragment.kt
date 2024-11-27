package com.call.detector.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.call.detector.controller.AppsAdapter
import com.call.detector.databinding.FragmentBackgroundAppsBinding
import com.call.detector.model.AppInfo

class BackgroundAppsFragment : Fragment() {

    private lateinit var binding: FragmentBackgroundAppsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBackgroundAppsBinding.inflate(layoutInflater)

        val appsWithMicAccess = AppInfo(this.requireContext()).getAppsWithBackgroundMicPermission()
        if (appsWithMicAccess.isNotEmpty()) {
            val apps = AppInfo(this.requireContext()).getAppsWithBackgroundMicPermission()
            val adapter = AppsAdapter(apps)
            binding.backgroundAppsFragmentRecyclerView.adapter = adapter

        } else {
            //Toast.makeText(this.context, "No apps with microphone access found", Toast.LENGTH_SHORT).show()
            binding.backgroundAppsFragmentImageNotFoundRelativeLayout.isVisible = true
        }

        binding.backgroundAppsFragmentRecyclerView.layoutManager = LinearLayoutManager(this.context)


        return binding.root
    }
}
