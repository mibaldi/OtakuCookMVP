package com.mibaldipabjimcas.otakucookmvp.Services.Broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;

/**
 * Created by mikelbalducieldiaz on 24/7/16.
 */
public class MyAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification;


        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
       Recipe recipe = intent.getParcelableExtra("recipe");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        notification = builder.setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(context.getString(R.string.recipe_finished))
                .setContentText(recipe.name)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(0, notification);
    }
}
