package models;

import java.util.Date;

public class recu {
    int id;
    String name_client;
    Double impot,total,mtotal;
    Date date;

    public recu(String name_client, Double impot, Double total, Double mtotal, Date date) {
        this.name_client = name_client;
        this.impot = impot;
        this.total = total;
        this.mtotal = mtotal;
        this.date = date;
    }

    public recu(int id, String name_client, Double impot, Double total, Double mtotal, Date date) {
        this.id = id;
        this.name_client = name_client;
        this.impot = impot;
        this.total = total;
        this.mtotal = mtotal;
        this.date = date;
    }

    public recu() {
        this.id = id;
        this.name_client = name_client;
        this.impot = impot;
        this.total = total;
        this.mtotal = mtotal;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_client() {
        return name_client;
    }

    public void setName_client(String name_client) {
        this.name_client = name_client;
    }

    public Double getImpot() {
        return impot;
    }

    public void setImpot(Double impot) {
        this.impot = impot;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getMtotal() {
        return mtotal;
    }

    public void setMtotal(Double mtotal) {
        this.mtotal = mtotal;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
