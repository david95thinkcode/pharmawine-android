package com.jmaplus.pharmawine.database.model;

public class DailyReportNotSent {


    private long id;
    private String report;
    private String creationDate;
    private Boolean status;
    private String dailyReportStart;
    private String dailyReportEnd;
    private Boolean isStartRequestSent;
    private Boolean isEndRequestSent;

    // TODO: ajouter aussi les proprietes suivante :
    // - DailyReportStart: DailyReportStart
    // - DailyReportEnd : DailyReportEnd
    // - isStartRequestSent : Boolean
    // - isEndRequestSent : Boolean

    public DailyReportNotSent() {

    }

    public DailyReportNotSent(long id, String report, String creationDate, String dailyReportStart, String dailyReportEnd, Boolean isEndRequestSent, Boolean isStartRequestSent, Boolean status) {
        this.id = id;
        this.report = report;
        this.dailyReportStart = dailyReportStart;
        this.dailyReportEnd = dailyReportEnd;
        this.isStartRequestSent = isStartRequestSent;
        this.isEndRequestSent = isEndRequestSent;
        this.creationDate = creationDate;
        this.status = status;
    }


//    public DailyReportNotSent(int id, String report, String creationDate, Boolean status) {
//        this.id = id;
//        this.report = report;
//        this.creationDate = creationDate;
//        this.status = status;
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDailyReportStart() {
        return dailyReportStart;
    }

    public void setDailyReportStart(String dailyReportStart) {
        this.dailyReportStart = dailyReportStart;
    }

    public String getDailyReportEnd() {
        return dailyReportEnd;
    }

    public void setDailyReportEnd(String dailyReportEnd) {
        this.dailyReportEnd = dailyReportEnd;
    }

    public Boolean getStartRequestSent() {
        return isStartRequestSent;
    }

    public void setStartRequestSent(Boolean startRequestSent) {
        isStartRequestSent = startRequestSent;
    }

    public Boolean getEndRequestSent() {
        return isEndRequestSent;
    }

    public void setEndRequestSent(Boolean endRequestSent) {
        isEndRequestSent = endRequestSent;
    }
}
