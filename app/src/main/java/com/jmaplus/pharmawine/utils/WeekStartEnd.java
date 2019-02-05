package com.jmaplus.pharmawine.utils;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;

public class WeekStartEnd {

    private Date startDate;
    private Date endDate;

    public WeekStartEnd() {
        startDate = new Date();
        endDate = new Date();
    }

    public WeekStartEnd(Date date) {
        calculateStartAndEndDate(date);
    }

    public void calculateStartAndEndDate(Date selectedDate) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
        Calendar c = Calendar.getInstance();
        c.setTime(selectedDate);

        // month doesn't have the correct index
        // let's set it to the correct month number
        c.add(Calendar.MONTH, 1);

        int originalMonth = c.get(Calendar.MONTH);

        // Getting monday date (start date)
        int mondayIndex = Calendar.MONDAY;
        int differenceOfDaysSinceMonday = c.get(Calendar.DAY_OF_WEEK) - mondayIndex;

        c.add(Calendar.DAY_OF_MONTH, -differenceOfDaysSinceMonday);
        startDate = c.getTime(); // Monday of the current week
        // At this point, startDate contains monday date

        while (c.get(Calendar.DAY_OF_WEEK) <= Calendar.FRIDAY) {

            // Get the real start day of the week
            // Important because monday could be not the week start date
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(startDate);
            if (startCal.get(Calendar.MONTH) != originalMonth) {
                startDate = c.getTime();
            }

            if (c.get(Calendar.MONTH) == originalMonth) {
                endDate = c.getTime();
            }

            c.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }
}
