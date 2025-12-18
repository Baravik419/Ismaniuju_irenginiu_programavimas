package com.example.a2kdpart2;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.MediaPlayer;
import android.net.Uri;

public class MyReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "ch_alarm";


    private static Ringtone ringtone;
    static MediaPlayer player;

    @Override
    public void onReceive(Context context, Intent intent) {
        createChannel(context);

        Intent open = new Intent(context, AlarmActivity.class);
        open.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent contentPI = PendingIntent.getActivity(context, 2001, open, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder b = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Zadintuvas")
                .setContentText("Numatytas laikas atejo")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(contentPI);

        NotificationManagerCompat.from(context).notify(101, b.build());

        try {
            if (player == null) {
                player = MediaPlayer.create(context.getApplicationContext(), R.raw.sample);
                player.setLooping(true);
                player.setVolume(1f, 1f);
                player.start();
            } else if (!player.isPlaying()) {
                player.start();
            }
        } catch (Exception ignored) {}

        context.startActivity(open);

    }

    private void createChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel ch = new NotificationChannel(
                    CHANNEL_ID,
                    "Alarm channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager nm = context.getSystemService(NotificationManager.class);
            nm.createNotificationChannel(ch);
        }
    }
}
