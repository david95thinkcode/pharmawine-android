package com.jmaplus.pharmawine.utils;

import java.util.ArrayList;
import java.util.List;

public class WeekDays {
    private String startDate = "";
    private String endDate = "";
    private List<String> allDays = new ArrayList();
    private List<String> allDaysLabel = new ArrayList();
    private Boolean sameMonth = false;

    public List<String> getAllDaysLabel() {
        return allDaysLabel;
    }

    public void setAllDaysLabel(List<String> allDaysLabel) {
        this.allDaysLabel = allDaysLabel;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<String> getAllDays() {
        return allDays;
    }

    public void setAllDays(List<String> allDays) {
        this.allDays = allDays;
    }

    public Boolean getSameMonth() {
        return sameMonth;
    }

    public void setSameMonth(Boolean sameMonth) {
        this.sameMonth = sameMonth;
    }


}


