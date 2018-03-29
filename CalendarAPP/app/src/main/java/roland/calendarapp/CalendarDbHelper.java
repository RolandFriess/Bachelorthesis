package roland.calendarapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Roland on 01.03.2018.
 */

public class CalendarDbHelper extends SQLiteOpenHelper {
    public static final int database_version = 1;
    // Anfrage für das Erstellen der Tabelle wird definert
    public String CREATE_QUERY = "CREATE TABLE " + CalendarBaseInfo.Table_info.TABLE_NAME +
            "(" + CalendarBaseInfo.Table_info._ID + " TEXT,"+
            CalendarBaseInfo.Table_info.CALENDAR_ID +" TEXT," +
            CalendarBaseInfo.Table_info.TITLE +" TEXT,"
            + CalendarBaseInfo.Table_info.DTSTART + "TEXT," +
            CalendarBaseInfo.Table_info.DTEND + "TEXT;";

    public CalendarDbHelper(Context context)

    {
        super(context, CalendarBaseInfo.Table_info.DATABASE_NAME,null,database_version);
        Log.d("Database operations", "Table created");
    }
    @Override
    public void onCreate(SQLiteDatabase sdb)
    {

        sdb.execSQL(CREATE_QUERY);
    }
    //Put Information in the SQL DATABASE
    public void putEvent(CalendarDbHelper dop, String title,String calender_id, String _id, String dtstart, String dtend)
    {
        SQLiteDatabase sq = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CalendarBaseInfo.Table_info.TITLE, title);
        cv.put(CalendarBaseInfo.Table_info.CALENDAR_ID, calender_id);
        cv.put(CalendarBaseInfo.Table_info._ID, _id);
        cv.put(CalendarBaseInfo.Table_info.DTSTART, dtstart);
        cv.put(CalendarBaseInfo.Table_info.DTEND, dtend);
        long k = sq.insert(CalendarBaseInfo.Table_info.TABLE_NAME,null,cv);

        Log.d("Database Operations", "One row inserted");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
