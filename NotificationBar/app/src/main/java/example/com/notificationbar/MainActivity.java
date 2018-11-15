package example.com.notificationbar;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button start;
    NotificationCompat.Builder notification;
    PendingIntent pIntent;
    NotificationManager manager;
    Intent resultIntent;
    TaskStackBuilder stackBuilder;

    NotificationManager mNotificationManager;
    String chanelId = "my_channel_01";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = (Button)findViewById(R.id.start);
        asignChanelNotification();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNotification();
//                 createNotification(view);
            }

        });
    }

    @SuppressLint("NewApi")
    private void asignChanelNotification(){
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //      The id of the channel.
        String id = chanelId;

        //      The user-visible name of the channel.
        CharSequence name = "myNotify";

        //      The user-visible description of the channel.
        String description = "How are you?";

        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel mChannel = new NotificationChannel(id, name,importance);

        //      Configure the notification channel.
        mChannel.setDescription(description);

        mChannel.enableLights(true);
        //      Sets the notification light color for notifications posted to this
        //      channel, if the device supports this feature.
        mChannel.setLightColor(Color.RED);

        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

        mNotificationManager.createNotificationChannel(mChannel);
    }

    @SuppressLint("NewApi")
    protected void startNotification() {

        notification = new NotificationCompat.Builder(MainActivity.this);
        //Tiêu đê
        notification.setContentTitle("Tiêu đê");
        //Nôị dung
        notification.setContentText("Nôi dung");
        //Thông báo khi nhận được notification
        notification.setTicker("Thông báo!");
        //Icon
        notification.setSmallIcon(R.drawable.ic_launcher);

        stackBuilder = TaskStackBuilder.create(MainActivity.this);
        stackBuilder.addParentStack(Result.class);

        resultIntent = new Intent(MainActivity.this, Result.class);
        stackBuilder.addNextIntent(resultIntent);
        notification.setAutoCancel(true);
        pIntent =  stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pIntent);
        notification.setChannelId(chanelId);

        manager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification.build());
    }

    // Other way to create notification
    @SuppressLint("NewApi")
    public void createNotification(View view) {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, Result.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        // Build notification
        // Actions are just fake

        Notification noti = new Notification.Builder(this)
                .setContentTitle("Tiêu đề")
                .setContentText("Nội dung")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pIntent)
                .setChannelId(chanelId)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);
    }
}