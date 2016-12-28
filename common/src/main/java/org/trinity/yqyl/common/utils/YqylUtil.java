package org.trinity.yqyl.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public final class YqylUtil {
    public static String randomServiceOrderId() {
        final DateFormat dateFormat = new SimpleDateFormat("ddyyyyMM");

        return String.format("%s%08d", dateFormat.format(new Date()), new Random().nextInt(99999999));
    }
}
