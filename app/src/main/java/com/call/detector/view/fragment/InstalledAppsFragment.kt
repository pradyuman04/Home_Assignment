package com.call.detector.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.call.detector.controller.AppsAdapter
import com.call.detector.databinding.FragmentInstalledAppsBinding
import com.call.detector.model.AppInfo

class InstalledAppsFragment : Fragment() {

    private lateinit var binding: FragmentInstalledAppsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentInstalledAppsBinding.inflate(layoutInflater)

        val appsWithMicAccess = AppInfo(this.requireContext()).getInstalledAppsWithMicAccess()
        if (appsWithMicAccess.isNotEmpty()) {
            val adapter = AppsAdapter(appsWithMicAccess)
            binding.installedAppsFragmentRecyclerView.adapter = adapter
        } else {
            //Toast.makeText(this.context, "No apps with microphone access found", Toast.LENGTH_SHORT).show()
            binding.installedAppsImageNotFoundRelativeLayout.isVisible = true
        }

        binding.installedAppsFragmentRecyclerView.layoutManager = LinearLayoutManager(this.context)

        return binding.root
    }
}
