package com.call.detector.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat

@Suppress("DEPRECATION")
class CallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            if (state == TelephonyManager.EXTRA_STATE_RINGING && incomingNumber != null) {
                // Start the service to display the popup
                val serviceIntent = Intent(context, CallOverlayService::class.java)
                serviceIntent.putExtra("incomingNumber", incomingNumber)
                ContextCompat.startForegroundService(context, serviceIntent)
            }

            if (state == TelephonyManager.EXTRA_STATE_IDLE) {
                // Stop the service when the call ends
                val stopIntent = Intent(context, CallOverlayService::class.java)
                context.stopService(stopIntent)
            }
        }
    }
}



