package roland.calendarapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Test extends AppCompatActivity {
    private static int MY_PERMISSIONS_REQUEST_READ_CALENDAR = 0;
    public static final String[] EVENT_PROJECTION = new String[]{
            CalendarContract.Events.TITLE,
            CalendarContract.Events.CALENDAR_ID,
            CalendarContract.Events._ID,
            CalendarContract.Events.CALENDAR_DISPLAY_NAME,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND,
            CalendarContract.Events.EVENT_LOCATION,

    };
    private String selection = CalendarContract.Events.CALENDAR_ID +"= '3'";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
    public void testen(View view)

    {

        Uri uri = CalendarContract.Events.CONTENT_URI;

        ContentResolver contentResolver = getContentResolver();
        //Daten abrufen
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CALENDAR},
                    MY_PERMISSIONS_REQUEST_READ_CALENDAR);
        }
        else {
            Cursor cur = contentResolver.query(uri, EVENT_PROJECTION, selection, null, null);
            Log.d("Anzahl Reihen original", " " + cur.getCount() + " ");
            Log.d("Anzahl Spalten original", " " + cur.getColumnCount() + " ");

            while (cur.moveToNext()) {
                for (int i = 0; i < cur.getColumnCount(); i++) {
                    Log.d("Erste Ausgabe " + cur.getColumnName(i), cur.getString(i) + "Reihennummer" + cur.getPosition());

                }
            }
            Cursor cursorneu = MainActivity.datenFiltern(cur);
            Log.d("Anzahl Reihen", " " + cursorneu.getCount() + " ");
            Log.d("Anzahl Spalten", " " + cursorneu.getColumnCount() + " ");
            cursorneu.move(-1);
            while (cursorneu.moveToNext()) {
                for (int i = 0; i < cursorneu.getColumnCount(); i++) {
                    Log.d("Zweite Ausgabe " + cursorneu.getColumnName(i) + ":", cursorneu.getString(i)+ " Reihennummer " + cursorneu.getPosition());

                }
            }

        }
    }

}
