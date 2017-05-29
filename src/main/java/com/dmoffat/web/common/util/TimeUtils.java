package com.dmoffat.web.common.util;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * @author dan on 11/04/2017.
 */
public class TimeUtils {
    /**
     * Returns the difference (in days) between the two LocalDateTime objects.
     *
     * Taken directly from:
     * http://stackoverflow.com/questions/26036671/difference-between-two-localdatetime-objects-in-days
     *
     * @param start
     * @param end
     * @return
     */
    public static int differenceInDays(LocalDateTime start, LocalDateTime end) {
        LocalDate startDate = start.toLocalDate();
        LocalDate endDate = end.toLocalDate();
        if(start.toLocalTime().isAfter(end.toLocalTime())) {
            startDate = startDate.plusDays(1);
        }
        return Days.daysBetween(startDate, endDate).getDays();
    }

    /**
     * Returns the number of days in a month.
     * @param date
     * @return
     */
    private static int getNumberOfDaysInMonth(LocalDateTime date) {
        return date.dayOfMonth().withMaximumValue().getDayOfMonth();
    }

    /**
     * Returns an array consisting of the number of days in the given month.
     *
     * E.g. For January, would return this array, where .. represents the numbers inbetween 2 and 30:
     * [1, 2, .. , 30, 31]
     *
     * @param date
     * @return
     */
    public static int[] getDaysOfMonthArray(LocalDateTime date) {
        int daysInMonth = getNumberOfDaysInMonth(date);

        int[] arr = new int[daysInMonth];

        for(int i = 1; i <= daysInMonth; i++) {
            arr[i - 1] = i;
        }

        return arr;
    }

    public static int getFirstDayOfMonthIndex(LocalDateTime date) {
        return date.withDayOfMonth(1).getDayOfWeek() - 1;
    }



}
