package com.call.detector.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.call.detector.controller.AppsAdapter
import com.call.detector.databinding.FragmentAllAppsBinding
import com.call.detector.model.AppInfo

class AllAppsFragment : Fragment() {

    private lateinit var binding: FragmentAllAppsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllAppsBinding.inflate(layoutInflater)

        val appsWithMicAccess = AppInfo(this.requireContext()).getAllAppsWithMicAccess()
        if (appsWithMicAccess.isNotEmpty()) {
            val adapter = AppsAdapter(appsWithMicAccess)
            binding.allAppsFragmentRecyclerView.adapter = adapter
        } else {
            //Toast.makeText(this.context, "No apps with microphone access found", Toast.LENGTH_SHORT).show()
            binding.allAppsFragmentImageNotFoundRelativeLayout.isVisible = true
        }
        binding.allAppsFragmentRecyclerView.layoutManager = LinearLayoutManager(this.context)
        return binding.root

    }
}
