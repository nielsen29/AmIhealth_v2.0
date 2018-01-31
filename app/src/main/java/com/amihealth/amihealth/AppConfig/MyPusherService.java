package com.amihealth.amihealth.AppConfig;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.amihealth.amihealth.AppConfig.notification.NewMessageNotification;
import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.Home.HomeActivity;
import com.amihealth.amihealth.Models.AmIHealthNotificacion;
import com.amihealth.amihealth.R;
import com.google.gson.Gson;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

import io.realm.Realm;

public class MyPusherService extends Service {




    public MyPusherService() {

    }

    @Override
    public IBinder onBind(Intent intent) {


        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        PusherOptions options = new PusherOptions().setCluster("mt1");
        Pusher pusher = new Pusher("c3d7a44ab3e34582316d", options);

        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                System.out.println("State changed to " + change.getCurrentState() +
                        " from " + change.getPreviousState());
            }

            @Override
            public void onError(String message, String code, Exception e) {
                System.out.println("There was a problem connecting!");
            }
        }, ConnectionState.ALL);

// Subscribe to a channel
        Channel channel = pusher.subscribe("user."+ new SessionManager(getApplicationContext()).getUserLogin().get(SessionManager.KEY).toString());

// Bind to listen for events called "my-event" sent to "my-channel"
        channel.bind("new-notification", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channel, String event, String data) {
                //System.out.println("Received event with data: " + data);
                //JSONObject jdo = new JSONObject(data).getJSONObject()

                Gson gson = new Gson();
                AmIHealthNotificacion notificacion = gson.fromJson(data, AmIHealthNotificacion.class);
                Log.v("SERVICIO_NOTIFY", "llego el evento para: "+notificacion.getTo());
                //showNotification(notificacion);
                //newNotify(notificacion);
                NewMessageNotification.notify(getApplicationContext(),notificacion, 1);
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.insertOrUpdate(notificacion);
                    }
                });
                realm.close();

            }
        });


// Reconnect, with all channel subscriptions and event bindings automatically recreated
        pusher.connect();
// The state change listener is notified when the connection has been re-established,
// the subscription to "my-channel" and binding on "my-event" still exist.
    }


    private void newNotify(AmIHealthNotificacion notificacion){
         NewMessageNotification.notify(getApplicationContext(),notificacion,1);
    }


    private void showNotification(AmIHealthNotificacion platziNotificacion){
        Intent intent = new Intent(this, HomeActivity.class);
        //intent.putExtra(KEY_DESCOUNT, platziNotificacion.getDescount());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent
                = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);

        //int icon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.amihealth);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setLargeIcon(null)
                .setContentTitle(platziNotificacion.getTitle())
                .setContentText(platziNotificacion.getBody())
                .setAutoCancel(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setShowWhen(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager
                = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        notificationManager.notify(0, notificationBuilder.build());

    }

    private void showNotificationApiK(AmIHealthNotificacion platziNotificacion){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        //intent.putExtra(KEY_DESCOUNT, platziNotificacion.getDescount());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent
                = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);

        //int icon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.amihealth);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_corazon)
                .setContentTitle(platziNotificacion.getTitle())
                .setContentText(platziNotificacion.getBody())
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setShowWhen(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager
                = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        notificationManager.notify(0, notificationBuilder.build());

    }
}
