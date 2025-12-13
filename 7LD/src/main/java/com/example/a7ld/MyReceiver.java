package com.example.a7ld;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;
import static androidx.core.content.ContextCompat.getSystemService;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class MyReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "Chanel1";
    String result, textTitle, textContent;
    TextView textView;
    Context context;
    int counter = 0;

    MyReceiver(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        counter++;

        createNotificationChannel();
        result = intent.getStringExtra("pranesimas");
        Log.d("EB", "onReceive() veikia, pranesimas = " + result);
        textView.setText(result);

        String action = intent.getAction();
        if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
            boolean isBatteryChanged = intent.getBooleanExtra("state", false);
            result = "Baterijos lygis pasikeite";
        }

        textTitle = "Mano pranesimas";
        textContent = "Pranesimo tekstas: " + result;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat.from(context).notify(255 + counter, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Mano pranesimas";
            String description = "Aprasymas";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
