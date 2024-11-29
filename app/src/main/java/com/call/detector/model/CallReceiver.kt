package com.call.detector.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public class CallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(intent.getAction())) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
                // Start the service to display the popup
                Intent serviceIntent = new Intent(context, CallOverlayService.class);
                serviceIntent.putExtra("incomingNumber", incomingNumber != null ? incomingNumber : "Unknown");
                context.startService(serviceIntent);
            }
        }
    }
}

