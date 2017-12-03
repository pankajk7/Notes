package com.test.note.pankaj.notes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Pankaj on 03/12/17.
 */

public class Utils {

    public static String getFormattedDateTime(long timeStamp) {
        Calendar inDate = Calendar.getInstance();
        inDate.setTimeInMillis(timeStamp);
        inDate.setTimeZone(TimeZone.getTimeZone("IST"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        return sdf.format(inDate.getTime());
    }
}
