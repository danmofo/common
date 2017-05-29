
<#macro calendarMonth calendarMonthView>

    <#-- Helper variables -->
        <#assign previousMonth=calendarMonthView.getPreviousMonth() />
        <#assign nextMonth=calendarMonthView.getNextMonth() />
        <#assign previousYear=calendarMonthView.getPreviousYear() />
        <#assign nextYear=calendarMonthView.getNextYear() />

        <#assign calendarEventsAdded = 0 />


        <h2 class="h3">${calendarMonthView.getMonthName()}</h2>

        <table class="calendar">
            <tr>
                <th class="calendar__header">Monday</th>
                <th class="calendar__header">Tuesday</th>
                <th class="calendar__header">Wednesday</th>
                <th class="calendar__header">Thursday</th>
                <th class="calendar__header">Friday</th>
                <th class="calendar__header">Saturday</th>
                <th class="calendar__header">Sunday</th>
            </tr>

            <#list calendarMonthView.getItems() as day, items>
                <#-- Start weekly row -->
                    <#if day?counter % 7 == 1>
                        <tr>
                    </#if>
                    <#if day gt 0>
                        <#if items??>
                            <#if items?size gt 0>
                                <td class="calendar__cell calendar__cell--${calendarEventsAdded}">
                                    <#if items[0].getEndDate().getDayOfMonth() == day>
                                        <#assign calendarEventsAdded += 1 />
                                    </#if>
                                    <#else>
                                <td class="calendar__cell calendar__cell--new">
                            </#if>

                        </#if>
                        <span class="calendar__cell__day">${day}</span>
                        <div class="calendar__cell__content">
                            <#if items??>
                                <#if items?size gt 0>
                                    ${items[0].displayName}
                                </#if>
                            </#if>
                        </div>
                        </td>
                        <#else />
                        <#-- Days that belong to previous / next months -->
                            <td class="calendar__cell calendar__cell--disabled">
                                ${day?abs}
                            </td>
                    </#if>

                    <#-- End weekly row -->
                        <#if day?counter % 7 == 0>
                            </tr>
                        </#if>
            </#list>
        </table>
</#macro>
