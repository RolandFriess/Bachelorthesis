package c.roland.test;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.CursorWrapper;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.CalendarContract.Calendars;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static int MY_PERMISSIONS_REQUEST_READ_CALENDAR = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public static final String[] EVENT_PROJECTION = new String[] {
            Calendars._ID,                           // 0
            Calendars.ACCOUNT_NAME,                  // 1
            Calendars.CALENDAR_DISPLAY_NAME,         // 2
            Calendars.OWNER_ACCOUNT                  // 3
    };
    public static final String[] EVENT_PROJECTION_2 = new String[]{
            CalendarContract.Events._ID,
            CalendarContract.Events.CALENDAR_DISPLAY_NAME,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND,
            CalendarContract.Events.EVENT_LOCATION,

    };
    private static final int PROJECTION_ID_INDEX_E = 0;
    private static final int PROJECTION_CALENDAR_DISPLAY_NAME_INDEX = 1;
    private static final int PROJECTION_DTSTART_INDEX = 2;
    private static final int PROJECTION_DTEND_INDEX = 3;
    private static final int PROJECTION_EVENT_LOCATION_INDEX = 4;

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;


    public void datenAbrufen(View view) {
        Toast.makeText(this, "Methode angefangen", Toast.LENGTH_LONG).show();
        ContentResolver resolver = getContentResolver();
        Cursor cur = null;
        Uri uri = Calendars.CONTENT_URI;
        String selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND ("
                + Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + Calendars.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = null;
// Submit the query and get a Cursor object back.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CALENDAR},
                    MY_PERMISSIONS_REQUEST_READ_CALENDAR);


        } else {

            cur = resolver.query(uri, EVENT_PROJECTION, null, selectionArgs, null);
            Log.d("Klasse Cursor", cur.getClass().toString());
            Toast.makeText(this, cur.getClass().toString(), Toast.LENGTH_LONG).show();
            Log.d("Inhalt Kalender", cur.toString());
            cur.getColumnCount();
            for (int y = 0; y < cur.getColumnCount(); y++) {
                String s = cur.getColumnName(y);
                Log.d("Name Spalte 1", s);
            }
            int reihen = cur.getCount();
            Log.d("Anzahl Reihen", ("" + reihen));
            CursorWrapper cursorWrapper = (CursorWrapper) cur;
            Cursor cursorunwrapped = ((CursorWrapper) cur).getWrappedCursor();
            Log.d("Cursorart", cursorunwrapped.getClass().toString());



            while (cur.moveToNext()) {
                Toast.makeText(this, "While Schleife", Toast.LENGTH_LONG).show();
                Log.d("_id",cur.getString(0));
                Log.d("account_name",cur.getString(1));
                Log.d("calendar_displayName",cur.getString(2));
                Log.d("ownerAccount",cur.getString(3));
                long calID = 0;
                String displayName = null;
                String accountName = null;
                String ownerName = null;

                // Get the field values
                calID = cur.getLong(PROJECTION_ID_INDEX);
                displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
                accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
                ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);


            }
        }
    }
    public void datenAbrufenEvents(View view)
    {

        ContentResolver cr = getContentResolver();
        Uri uri2 = CalendarContract.Events.CONTENT_URI;
        String selection = "((" + CalendarContract.Events.CALENDAR_DISPLAY_NAME + " = ?) AND ("
                + CalendarContract.Events.DTSTART + " = ?) AND ("
                + CalendarContract.Events.DTEND + " = ?) AND ("+ CalendarContract.Events.EVENT_LOCATION +" = ?))";



        String[] selectionArgs = null;


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            Toast.makeText(this,"Leseerlaubnis nicht erteilt",Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CALENDAR},
                    MY_PERMISSIONS_REQUEST_READ_CALENDAR);

        }
        else {
            //Cursor wird angefragt
            Cursor cursor = cr.query(uri2, EVENT_PROJECTION_2, selection, selectionArgs , null);
            //Alle Zeilennamen werden ausgegeben im Logcat
            String[] s = cursor.getColumnNames();
            for(int x = 0; x<s.length;x++) {

                        Log.d("Column Name" + x + ":", s[x]);
            }


            //Typecast zu CursorWrapper, Klasse wurde vorher überprüft
            CursorWrapper cursorWrapper = (CursorWrapper) cursor;

            //Abruf des Cursors der gewrappt wurde
            Cursor cursorunwrapped = ((CursorWrapper) cursor).getWrappedCursor();

           //Ausgabe der Cursorart
            Log.d("Cursorart" ,cursorunwrapped.getClass().toString());

            //Typecast zu einem AbstractWindowedCursor
            AbstractWindowedCursor abstractWindowedCursor = (AbstractWindowedCursor) cursorunwrapped;

            //Ausgabe des Cursorwindows
            Log.d("Cursorwindow", abstractWindowedCursor.getWindow().toString());
            CursorWindow window = abstractWindowedCursor.getWindow();

            Log.d("Anzahl der Reihen vor",String.valueOf(window.getNumRows()));
            CursorWindow windownew = new CursorWindow("RolandsWindow");

            windownew.setNumColumns(2);
            windownew.allocRow();
            windownew.allocRow();
            windownew.putString("Funktioniert",1,1);

            Log.d("Wert Spalte 1, Reihe 1 nach",windownew.getString(1,1));

            if(window.allocRow()==true) {
                Log.d("Reihe hinzufügen","Erfolgreich");
            }
            else
            {
                Log.d("Reihe hinzufügen","Nicht Erfolgreich");
            }
            window.allocRow();
            Log.d("Anzahl der Reihen nach",String.valueOf(window.getNumRows()));
            abstractWindowedCursor.setWindow(windownew);
            abstractWindowedCursor.moveToPosition(1);
            Log.d("Test ob neues Window referenziert ist",String.valueOf(abstractWindowedCursor.getColumnCount()));
                   //for(int x=0;x<cursor.getColumnCount();x++)
            //{
              //  Log.d("Column names", cursor.getColumnName(x));
                //cursor.moveToFirst();
                //Log.d("Inhalt", cursor.getString(x));
                //while(cursor.moveToNext()== true)
                //{
                 //   Log.d(cursor.getColumnName(x),cursor.getString(x));
                //}
            }

        }



    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


}
