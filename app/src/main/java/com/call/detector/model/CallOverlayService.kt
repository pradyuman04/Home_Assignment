package com.call.detector.model

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.call.detector.R

class CallOverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private var overlayView: View? = null
    private var isOverlayVisible = false // Flag to track overlay visibility

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val incomingNumber = intent?.getStringExtra("incomingNumber") ?: "Unknown"

        // Start foreground notification
        startForegroundNotification()

        // Display the overlay only if it's not already visible
        if (!isOverlayVisible) {
            showOverlay(incomingNumber)
        }

        return START_NOT_STICKY
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showOverlay(incomingNumber: String) {
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        // Inflate the overlay layout
        overlayView = LayoutInflater.from(this).inflate(R.layout.overlay_layout, null)

        // Update the overlay with incoming caller info
        val callerNameTextView: TextView? = overlayView?.findViewById(R.id.callerNumber)
        callerNameTextView?.text = "Caller: $incomingNumber"

        // Close button logic
        val closeButton: Button? = overlayView?.findViewById(R.id.closeOverlayButton)
        closeButton?.setOnClickListener {
            removeOverlay()
        }

        // Set layout parameters for the overlay
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.CENTER // Default position in the center

        // Add the overlay to the window
        windowManager.addView(overlayView, params)
        isOverlayVisible = true // Set the flag to true

        // Enable dragging for the overlay
        overlayView?.setOnTouchListener(object : View.OnTouchListener {
            private var initialX = 0
            private var initialY = 0
            private var touchX = 0f
            private var touchY = 0f

            @SuppressLint("ClickableViewAccessibility")
            override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = params.x
                        initialY = params.y
                        touchX = motionEvent.rawX
                        touchY = motionEvent.rawY
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        params.x = initialX + (motionEvent.rawX - touchX).toInt()
                        params.y = initialY + (motionEvent.rawY - touchY).toInt()
                        windowManager.updateViewLayout(overlayView, params)
                        return true
                    }
                }
                return false
            }
        })
    }

    private fun removeOverlay() {
        if (overlayView != null && isOverlayVisible) {
            windowManager.removeView(overlayView)
            overlayView = null
            isOverlayVisible = false // Reset the flag
        }
    }

    private fun startForegroundNotification() {
        val notificationChannelId = "call_overlay_service_channel"

        // Create a notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelId,
                "Call Overlay Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Build the notification
        val notification = NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle("Call Overlay Service")
            .setContentText("Running in the background")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with your app icon
            .build()

        // Start the service in the foreground
        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeOverlay() // Ensure the overlay is removed when the service is destroyed
    }

    override fun onBind(intent: Intent?): IBinder? = null
}



