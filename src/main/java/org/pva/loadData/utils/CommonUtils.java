package org.pva.loadData.utils;

public class CommonUtils {

    public static String nanosToMinSec(long nanos) {
        long totalSecs = nanos / 1000_000_000L;
        long min = totalSecs / 60;
        long sec = totalSecs % 60;
        return String.format("%d min %d sec", min, sec);
    }
}
