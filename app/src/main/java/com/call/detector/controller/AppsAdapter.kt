package com.call.detector.controller

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.call.detector.R
import com.call.detector.model.AppList

class AppsAdapter(private val apps: List<AppList>) : RecyclerView.Adapter<AppsAdapter.AppViewHolder>() {

    class AppViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val itemLinearLayout: LinearLayout = view.findViewById(R.id.itemLinearLayout)
        val appIcon: ImageView = view.findViewById(R.id.appIcon)
        val appName: TextView = view.findViewById(R.id.appName)
       // val appPackageName: TextView = view.findViewById(R.id.appPackageName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false)


        return AppViewHolder(view)
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val app = apps[position]
        holder.appIcon.setImageDrawable(app.icon)
        holder.appName.text = app.name
       // holder.appPackageName.text = app.packageName

        holder.itemLinearLayout.setOnClickListener {

            val context = holder.view.context
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.parse("package:${app.packageName}")
            }
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int = apps.size
}
