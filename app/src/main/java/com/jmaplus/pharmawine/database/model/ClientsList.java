package com.jmaplus.pharmawine.database.model;

public class ClientsList {


    private long id;
    private String client;
    private String dateProspection;


    public ClientsList() {


    }

    public ClientsList(String client, String dateProspection) {
        this.client = client;
        this.dateProspection = dateProspection;
    }

    public ClientsList(long id, String client, String dateProspection) {
        this.id = id;
        this.client = client;
        this.dateProspection = dateProspection;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDateProspection() {
        return dateProspection;
    }

    public void setDateProspection(String dateProspection) {
        this.dateProspection = dateProspection;
    }
}

