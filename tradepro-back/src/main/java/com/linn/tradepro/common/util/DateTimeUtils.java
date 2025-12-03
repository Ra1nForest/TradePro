package com.linn.tradepro.common.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateTimeUtils {
    public static String getCurrentDateTime() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(currentTimestamp);
    }
}
