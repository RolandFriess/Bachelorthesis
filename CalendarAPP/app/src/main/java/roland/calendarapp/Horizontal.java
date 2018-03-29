package roland.calendarapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Horizontal extends AppCompatActivity {
    public static String[] horizontalerFilter;
    private int counter;
    public String[] calendartypes;
    private Spinner spinn;
    private static final String[] projection = {CalendarContract.Calendars.CALENDAR_DISPLAY_NAME};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal);
        spinn = findViewById(R.id.spinner);
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        ContentResolver resolver = getContentResolver();
        Cursor cur = resolver.query(uri,projection,null,null,null);
        calendartypes = new String[cur.getCount()];
        while(cur.moveToNext())
        {
            calendartypes[cur.getPosition()] = cur.getString(0);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,calendartypes);
        Spinner spinn = findViewById(R.id.spinner);
        spinn.setAdapter(adapter);
        horizontalerFilter = new String[cur.getCount()];

    }
    public void filterHinzufuegen(View view)

    {
        String hinzu = spinn.getSelectedItem().toString();
        boolean enthalten = false;
        Log.d("filterHinzufuegen","Methode gestartet");

        for(int i = 0; i< calendartypes.length;i++) {
            Log.d("filterHinzufuegen","for-Schleife" + i);

            if (hinzu == horizontalerFilter[i]) {
                Log.d("filterHinzufuegen","schon enthalten");
                enthalten = true;
            }
        }
            if(enthalten == false)
            {
                Log.d("filterHinzufuegen","nicht enthalten");
                horizontalerFilter[counter] = hinzu;
                counter++;
                Log.d("filterHinzufuegen","Wert hinzugefuegt");
                Toast.makeText(this,hinzu +" hinzugefügt",Toast.LENGTH_SHORT).show();






            }
            else
            {
                Toast.makeText(this,"Element schon enthalten",Toast.LENGTH_LONG).show();
            }

        }
    }

