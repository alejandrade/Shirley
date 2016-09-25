package gigamog.com.stalker.gigamog.com.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

import gigamog.com.stalker.MainActivity;
import gigamog.com.stalker.R;

/**
 * Created by aleja on 9/25/2016.
 */

public class StalkerService extends IntentService {
    private NotificationManager mNotificationManager;
    public static final int NOTIFICATION_ID = 1;
    public static final String TRANSITION_INTENT_SERVICE = "ReceiveTransitionsIntentService";


    public StalkerService() {
        super("jill");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle dataString = intent.getExtras();
        String theResposne = dataString.get("extraText").toString();
        Log.d("Gigamog", theResposne);
        startStalking();
    }

    private void startStalking(){
        sendNotification("I'm watching you :).",3);
        sendNotification("who is "+lastContact(),6);


    }


    private String lastContact(){
        String name =  "";
        ContentResolver cr = getContentResolver();
        String[] projection = {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER,
                ContactsContract.Contacts.TIMES_CONTACTED,
                ContactsContract.Contacts.LAST_TIME_CONTACTED
        };
        //String selection = String.format("%s > 0", ContactsContract.Contacts.HAS_PHONE_NUMBER);

        String[] selectionArgs = null;
        String sortOrder = String.format(
                "%s DESC",
                ContactsContract.Contacts.LAST_TIME_CONTACTED
        );

        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                projection, null, selectionArgs, sortOrder);
        int contactCount = cur.getCount();

        cur.moveToPosition(0);
        String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
        name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));


        cur.close();

        return name;
    }




    private void sendNotification(String msg, int secondsDelday) {

        try {
            Thread.sleep(secondsDelday*1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Hey.")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setSmallIcon(R.drawable.notification_icon)
                        .setSound(uri)
                        .setContentText(msg);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

}
