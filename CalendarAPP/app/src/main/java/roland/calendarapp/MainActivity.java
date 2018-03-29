package roland.calendarapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public static Boolean[] vertikalerFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AvareCalendarProvider avare = new AvareCalendarProvider();
        vertikalerFilter = new Boolean[]{
                false,
                //Filter aktiviert für TITLE
                false,
                //Filter aktiviert für Startzeit
                false,
                //Filter aktiviert für Endzeit
                false
                //Filter aktiviert für Event_Location
        };

    }

    public void verrauschen(View view) {
        Intent intent = new Intent(this, Verrauschen.class);
        startActivity(intent);

    }

    public void filtern(View view) {
        Intent intent = new Intent(this, Filtern.class);
        startActivity(intent);

    }

    public void blockieren(View view) {
        Intent intent = new Intent(this, Blockieren.class);
        startActivity(intent);

    }

    public void testen(View view) {
        Intent intent = new Intent(this, Test.class);
        startActivity(intent);
    }

    public static Cursor datenFiltern(Cursor cursor) {
        boolean horizontaleFilterungMoeglich = false;

        //Nachschauen, ob die Kalendar_ID mit übergeben wurde true falls es übergeben wurde
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            if (cursor.getColumnName(i).equals(CalendarContract.Events.CALENDAR_DISPLAY_NAME)) {
                horizontaleFilterungMoeglich = true;
            }
        }


        Log.d("datenFiltern", "Horizontaler Filter moeglich" + horizontaleFilterungMoeglich);
        //Abrufen der Spaltennamen
        String[] columnNames = cursor.getColumnNames();
        //Erstellen eines neuen MatrixCursors mit den Spaltennamen
        MatrixCursor matrixCursorneu = new MatrixCursor(columnNames);
        Log.d("datenFiltern", "MatrixCursor erstellt" + matrixCursorneu.toString());


        //Cursor an erste Stelle setzen
        cursor.moveToFirst();
        //Überprüfen ob horizontale Filterung möglich ist
        if (Filtern.horizontalerFilterakt && horizontaleFilterungMoeglich) {
            // For-Schleife, die durch alle Reihen nacheinander durchgeht
            Log.d("datenFiltern", "Beginn Horizontale Filterung");
            for (int x = 0; x < cursor.getCount(); x++) {
                //Horizontale Filterung
                //Überprüfen ob Reihe übernommen wird
                boolean wirduebernommen = true;

                for (int y = 0; y < Horizontal.horizontalerFilter.length; y++) {
                    String cal_ID = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.CALENDAR_ID));
                    if (cal_ID == Horizontal.horizontalerFilter[y]) {
                        wirduebernommen = false;
                        Log.d("datenFiltern", "Wird nicht uebernommen");
                    }
                }

                if (wirduebernommen) {
                    //Daten in neuen Cursor schreiben

                    Log.d("datenFiltern", "Wird uebernommen in Durchlauf " + x);

                    Object[] neueReihe = new Object[cursor.getColumnCount()];
                    Log.d("Vertikaler Filter","" + Filtern.vertikalerFilterakt);
                    for (int z = 0; z < cursor.getColumnCount(); z++) {

                        if (Filtern.vertikalerFilterakt) {
                            if (vertikalerFilter[0] == true && CalendarContract.Events.TITLE == cursor.getColumnName(z) ||
                                    vertikalerFilter[1] == true && CalendarContract.Events.DTSTART == cursor.getColumnName(z) ||
                                    vertikalerFilter[2] == true && CalendarContract.Events.DTEND == cursor.getColumnName(z) ||
                                    vertikalerFilter[3] == true && CalendarContract.Events.EVENT_LOCATION == cursor.getColumnName(z)) {
                                neueReihe[z] = null;
                            }
                        } else {
                            String add = cursor.getString(z);
                            neueReihe[z] = add;
                        }

                    }
                    matrixCursorneu.addRow(neueReihe);

                }
                else
                {

                }


            }
        } else {
            for (int x = 0; x < cursor.getCount(); x++) {


                Object[] neueReihe = new Object[cursor.getColumnCount()];
                for (int z = 0; z < cursor.getColumnCount(); z++) {


                    if (Filtern.vertikalerFilterakt && vertikalerFilter[0] == true && CalendarContract.Events.TITLE == cursor.getColumnName(z) ||
                            vertikalerFilter[1] == true && CalendarContract.Events.DTSTART == cursor.getColumnName(z) ||
                            vertikalerFilter[2] == true && CalendarContract.Events.DTEND == cursor.getColumnName(z) ||
                            vertikalerFilter[3] == true && CalendarContract.Events.EVENT_LOCATION == cursor.getColumnName(z)) {
                        neueReihe[z] = null;
                    } else {
                        String add = cursor.getString(z);
                        neueReihe[z] = add;
                    }
                }
                matrixCursorneu.addRow(neueReihe);

            }
        }
        Object[] nullReihe = new Object[cursor.getColumnCount()];
        for(int i = 0;i<cursor.getColumnCount();i++)
        {
            nullReihe[i] = null;
        }
        matrixCursorneu.addRow(nullReihe);

        return matrixCursorneu;

    }
}
