package com.example.a7ld;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyReceiver extends BroadcastReceiver {

    private static final int LOW_THRESHOLD = 15;

    @Override
    public void onReceive(Context context, Intent intent) {
        int i = 0;
        i = i + 1;

        SharedPreferences prefs =
                context.getSharedPreferences("ld7", Context.MODE_PRIVATE);

        boolean enabled = prefs.getBoolean("enabled", false);
        if (!enabled) return;

        int lastPercent = prefs.getInt("last_percent", -1);
        boolean lowNotified = prefs.getBoolean("low_notified", false);

        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int percent = (scale > 0) ? (level * 100 / scale) : -1;

        if (percent == lastPercent) return;

        createChannel(context);

        Intent openApp = new Intent(context, MainActivity.class);
        openApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pi = PendingIntent.getActivity(
                context,
                0,
                openApp,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        if (percent <= LOW_THRESHOLD && !lowNotified) {
            NotificationCompat.Builder lowBuilder =
                    new NotificationCompat.Builder(context, "battery_channel")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Žemas baterijos lygis")
                            .setContentText("Baterija nukrito iki " + percent + "%")
                            .setAutoCancel(true)
                            .setContentIntent(pi);

            NotificationManagerCompat.from(context).notify(100, lowBuilder.build());
            prefs.edit().putBoolean("low_notified", true).apply();
        }

        NotificationCompat.Builder changeBuilder =
                new NotificationCompat.Builder(context, "battery_channel")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Baterijos lygis pasikeitė")
                        .setContentText("Dabartinis lygis: " + percent + "%")
                        .setAutoCancel(true)
                        .setContentIntent(pi);

        NotificationManagerCompat.from(context).notify(101 + i, changeBuilder.build());

        if (percent > LOW_THRESHOLD) {
            prefs.edit().putBoolean("low_notified", false).apply();
        }

        prefs.edit().putInt("last_percent", percent).apply();
    }

    private void createChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(
                            "battery_channel",
                            "Battery channel",
                            NotificationManager.IMPORTANCE_DEFAULT
                    );

            NotificationManager manager =
                    context.getSystemService(NotificationManager.class);

            manager.createNotificationChannel(channel);
        }
    }
}
