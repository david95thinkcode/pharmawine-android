package com.jmaplus.pharmawine.database.model;

public class DailyReportNotSent {


    private int id;
    private String report;
    private String creationDate;
    private Boolean status;

    // TODO: ajouter aussi les proprietes suivante :
    // - DailyReportStart: DailyReportStart
    // - DailyReportEnd : DailyReportEnd
    // - isStartRequestSent : Boolean
    // - isEndRequestSent : Boolean

    public DailyReportNotSent() {

    }

    public DailyReportNotSent(String report, String creationDate, Boolean status) {
        this.report = report;
        this.creationDate = creationDate;
        this.status = status;
    }


    public DailyReportNotSent(int id, String report, String creationDate, Boolean status) {
        this.id = id;
        this.report = report;
        this.creationDate = creationDate;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
