package com.call.detector.model;

import android.app.Service;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.call.detector.R;

public class CallOverlayService extends Service {

    private WindowManager windowManager;
    private View overlayView;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String incomingNumber = intent != null ? intent.getStringExtra("incomingNumber") : "Unknown";

        // Create and display the overlay
        showOverlay(incomingNumber);
        return START_NOT_STICKY;
    }

    private void showOverlay(String incomingNumber) {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // Inflate the overlay layout
        overlayView = LayoutInflater.from(this).inflate(R.layout.overlay_layout, null);

        // Update the overlay with incoming caller info
        TextView callerNameTextView = overlayView.findViewById(R.id.callerName);
        callerNameTextView.setText("John Doe (" + incomingNumber + ")");

        // Set layout parameters for the overlay
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );
        params.gravity = Gravity.CENTER;

        // Add the overlay to the window
        windowManager.addView(overlayView, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Remove the overlay when the service is destroyed
        if (overlayView != null && windowManager != null) {
            windowManager.removeView(overlayView);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

