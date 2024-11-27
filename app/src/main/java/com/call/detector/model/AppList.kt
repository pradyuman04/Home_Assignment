package com.call.detector.model

import android.graphics.drawable.Drawable

data class AppList(
    val name: String,
    val packageName: String,
    val icon: Drawable,
    val hasBackgroundMicPermission: Boolean,
    val hasMicrophonePermission: Boolean,
    val isUsingMicrophone: Boolean,


)