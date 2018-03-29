package roland.calendarapp;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Roland on 01.03.2018.
 */

public class CalendarBaseInfo {
    public static final String CONTENT_AUTHORITY = "com.example.roland.calendardatahandler.Calendarbase";
    private static final Uri BASE_CONTENT_URI = Uri.parse(("content://" + CONTENT_AUTHORITY));
    public static final String PATH_CALENDAR = "calendar";

    public CalendarBaseInfo()
    {

    }
    public static abstract class Table_info implements BaseColumns
    {
        //All the Columns for the Table with their names
        public static final String CALENDAR_ID = "calendar_id";
        public static final String _ID = "_id";
        public static final String DTSTART = "dtstart";
        public static final String DTEND ="dtend";
        public static final String TITLE = "title";
        public static final String TABLE_NAME = "table_name";
        public static final String DATABASE_NAME = "Calendarbase";

    }
}
