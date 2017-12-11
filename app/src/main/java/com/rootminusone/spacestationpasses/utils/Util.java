package com.rootminusone.spacestationpasses.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by gangares on 12/10/17.
 */

public class Util {

    private static String TAG = Util.class.getCanonicalName();

    public static String getDateInCurrentTimeZone(String inputDate)
    {
        String outputDate = "";
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getDefault().getTimeZone("UTC"));
            String value = formatter.format(new Date(Long.valueOf(inputDate) * 1000L));
            Date dateValue = formatter.parse(value);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy HH:mm aa zzzz");
            dateFormatter.setTimeZone(TimeZone.getDefault());
            outputDate = dateFormatter.format(dateValue);
        }
        catch (Exception e)
        {
            Log.d(TAG, e.getMessage());
            //Handle and report exception here
        }
        return outputDate;
    }

    public static String convertToSeconds(String input) {
        if (TextUtils.isDigitsOnly(input)) {
            return String.valueOf(Long.valueOf(input) / 60);
        } else {
            return input;
        }
    }
}
