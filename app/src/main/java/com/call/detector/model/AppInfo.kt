package com.call.detector.model

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

@Suppress("DEPRECATION")
class AppInfo(private val context: Context) {

     fun getAllAppsWithMicAccess(): List<AppList> {

        val appsList = mutableListOf<AppList>()
        val packageManager = context.packageManager
        val installedPackages: List<PackageInfo> = packageManager!!.getInstalledPackages(
            PackageManager.GET_PERMISSIONS)

        for (packageInfo in installedPackages) {
            val requestedPermissions = packageInfo.requestedPermissions
            if (requestedPermissions != null && requestedPermissions.contains(android.Manifest.permission.RECORD_AUDIO)) {
                val appName = packageInfo.applicationInfo.loadLabel(packageManager).toString()
                val packageName = packageInfo.packageName
                val appIcon = packageInfo.applicationInfo.loadIcon(packageManager)

                appsList.add(AppList(name = appName,
                    packageName = packageName,
                    icon = appIcon,
                    hasBackgroundMicPermission = false,
                    hasMicrophonePermission = true,
                    isUsingMicrophone = false))
            }
        }

        return appsList
    }

    fun getInstalledAppsWithMicAccess(): List<AppList> {

        val packageManager = context.packageManager
        val installedApps = mutableListOf<AppList>()

        val packages = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS)
        for (packageInfo in packages) {
            val requestedPermissions = packageInfo.requestedPermissions ?: continue
            val applicationInfo = packageInfo.applicationInfo
            val isDownloadedFromPlayStore = (
                    applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0 &&  // Not a system app
                            packageManager.getInstallerPackageName(packageInfo.packageName) == "com.android.vending" // Installed via Play Store
                    )

            if (isDownloadedFromPlayStore && android.Manifest.permission.RECORD_AUDIO in requestedPermissions) {
                val appName = applicationInfo.loadLabel(packageManager).toString()
                val packageName = packageInfo.packageName
                val appIcon = applicationInfo.loadIcon(packageManager)

                installedApps.add(
                    AppList(
                        name = appName,
                        packageName = packageName,
                        icon = appIcon,
                        hasBackgroundMicPermission = false,
                        hasMicrophonePermission = true,
                        isUsingMicrophone = true
                    )
                )
            }
        }
        return installedApps
    }

    @SuppressLint("InlinedApi")
    fun getAppsWithBackgroundMicPermission(): List<AppList> {
        val packageManager = context.packageManager
        val appsWithBackgroundMicPermission = mutableListOf<AppList>()

        // Fetch installed packages with permissions info
        val packages = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS)
        for (packageInfo in packages) {
            val requestedPermissions = packageInfo.requestedPermissions ?: continue
            val applicationInfo = packageInfo.applicationInfo

            // Check for RECORD_AUDIO permission
            val hasMicPermission = android.Manifest.permission.RECORD_AUDIO in requestedPermissions

            // Check for additional permissions that suggest background microphone usage
            val hasBackgroundUsageCapability =
                android.Manifest.permission.FOREGROUND_SERVICE in requestedPermissions || android.Manifest.permission.SYSTEM_ALERT_WINDOW in requestedPermissions || android.Manifest.permission.BIND_ACCESSIBILITY_SERVICE in requestedPermissions

            if (hasMicPermission && hasBackgroundUsageCapability) {
                val appName = applicationInfo.loadLabel(packageManager).toString()
                val packageName = packageInfo.packageName
                val appIcon = applicationInfo.loadIcon(packageManager)

                appsWithBackgroundMicPermission.add(
                    AppList(
                        name = appName,
                        packageName = packageName,
                        icon = appIcon,
                        hasBackgroundMicPermission = true,
                        hasMicrophonePermission = true,
                        isUsingMicrophone = true
                    )
                )
            }
        }
        return appsWithBackgroundMicPermission
    }
}