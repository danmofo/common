package com.dmoffat.web.common.view.calendar;

import org.joda.time.LocalDateTime;

/**
 * Implement this interface to allow your objects to be placed on a CalendarMonthView
 *
 * @author dan on 21/03/2017.
 */
public interface CalendarEvent {
    LocalDateTime getStartDate();
    LocalDateTime getEndDate();
    String getDisplayName();
}
