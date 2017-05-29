package com.dmoffat.web.common.view.calendar;

import com.dmoffat.web.common.util.TimeUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Represents a month on the calendar, for use in templates. Once constructed, will contain a Map with an entry for each day of the month
 * and a list of CalendarEvents that occur on that day.
 *
 * This is useful for plotting things on a calendar in the view, for example:
 *
 * CalendarMonthView<Section> view = new CalendarMonthView(LocalDateTime.now(), sectionService.listThisMonth());
 *
 *
 * @author dan on 21/03/2017.
 */
public class CalendarMonthView<T extends CalendarEvent> {
        // LinkedHashMap retains insertion order. A regular hashmap makes no guarantee about iteration order.
        private HashMap<Integer, List<T>> items = new LinkedHashMap<>();
        private LocalDateTime date;

        private LocalDateTime previousMonth;
        private LocalDateTime nextMonth;
        private LocalDateTime previousYear;
        private LocalDateTime nextYear;

        private long itemCount;

        public CalendarMonthView(LocalDateTime date, List<T> calendarItems) {
            this.date = date;

            this.previousMonth = calculatePreviousMonth();
            this.nextMonth = calculateNextMonth();
            this.previousYear = calculatePreviousYear();
            this.nextYear = calculateNextYear();

            // Only count items which appear this month - todo: limit the month in the query...list upcoming this month
            this.itemCount = calendarItems.size();

            int[] daysOfMonth = TimeUtils.getDaysOfMonthArray(date);

            int start = date.withDayOfMonth(1).getDayOfWeek();

            // Amount of days to add before the start of the month
            int daysBeforeStartOfMonth = start - 1;

            int lastDayOfPreviousMonth = date.withDayOfMonth(1).minusDays(1).getDayOfMonth();

            // Add the days before the start of the month, with a negative number as the key,
            // otherwise the things will get mixed up because their key will be the same as days
            // from the previous month
            for(int j = lastDayOfPreviousMonth - (daysBeforeStartOfMonth - 1); j <= lastDayOfPreviousMonth; j++) {
                items.put(-j, new ArrayList<>());
            }

            // Initialise an empty list for day of the month to hold things, we need to add a list for each day even if
            // there wont be any items, since the view layer needs to iterate over the items map and not have to worry about
            // calendars.
            for (int i = 0; i < daysOfMonth.length; i++) {
                items.put(daysOfMonth[i], new ArrayList<>());
            }

            for (int i = 0; i <= daysOfMonth.length; i++) {

                for(T item: calendarItems) {

                    // If an event's start date matches the current calendar day, calculate the distance to the event's end date
                    // and add an entry to the calendar map for each day.
                    if(item.getStartDate().getDayOfMonth() == i) {
                        // This always assumes the end time on the end date is 23:59
                        long duration = TimeUtils.differenceInDays(item.getStartDate(), item.getEndDate().plusMinutes(1));
                        while(duration != 0) {
                            List<T> it = items.get(i);
                            if(it != null) {
                                items.get(i).add(item);
                            }
                            i++;
                            duration--;
                        }

                    }
                }
            }

            // If the start of the month lands on a Saturday / Sunday (6/7)
            // then we need to add an extra row to accommodate the overflow.
            int requiredSize = items.size() <= 35 ? 35 : 42;

            // Calculate the days to display from the next month
            int overlap = requiredSize - items.size();

            // Add the overlap to the end of the month
            for(int k = 1; k <= overlap; k++) {
                items.put(-k, null);
            }
        }

        public String getMonthName() {
            return DateTimeFormat.forPattern("MMMM").print(this.date);
        }

        private LocalDateTime calculatePreviousMonth() {
            return this.date.minusMonths(1);
        }

        private LocalDateTime calculatePreviousYear() {
            return this.date.minusYears(1);
        }

        private LocalDateTime calculateNextMonth() {
            return this.date.plusMonths(1);
        }

        private LocalDateTime calculateNextYear() {
            return this.date.plusYears(1);
        }

        public HashMap<Integer, List<T>> getItems() {
            return this.items;
        }

        public long getDayOfMonth() {
            return this.date.getDayOfMonth();
        }


        public LocalDateTime getPreviousMonth() {
            return previousMonth;
        }

        public LocalDateTime getNextMonth() {
            return nextMonth;
        }

        public LocalDateTime getPreviousYear() {
            return previousYear;
        }

        public LocalDateTime getNextYear() {
            return nextYear;
        }

        public long getItemCount() {
            return itemCount;
        }

        private CalendarMonthView() {}


    @Override
    public String toString() {
        String sb = "CalendarMonthView{" + "items=" + items +
                ", date=" + date +
                ", previousMonth=" + previousMonth +
                ", nextMonth=" + nextMonth +
                ", previousYear=" + previousYear +
                ", nextYear=" + nextYear +
                ", itemCount=" + itemCount +
                '}';
        return sb;
    }
}
