package models;

public class reservation {

    private int id, qte, periode, prix;
    private String model, heurersv, datersv;
    double impot,total,mtotal;




    public reservation(int id, int qte, int periode, int prix, String model, String heurersv, String datersv) {
        this.id = id;
        this.qte = qte;
        this.periode = periode;
        this.prix = prix;
        this.model = model;
        this.heurersv = heurersv;
        this.datersv = datersv;

    }

    public reservation() {
        this.id = id;
        this.qte = qte;
        this.periode = periode;
        this.prix = prix;
        this.model = model;
        this.heurersv = heurersv;
        this.datersv = datersv;
    }

    public reservation(int qte, int periode, int prix, String model, String heurersv, String datersv, double impot, double total, double mtotal) {
        this.qte = qte;
        this.periode = periode;
        this.prix = prix;
        this.model = model;
        this.heurersv = heurersv;
        this.datersv = datersv;
        this.impot = impot;
        this.total = total;
        this.mtotal = mtotal;
    }

    public reservation(int id, int qte, int periode, int prix, String model, String heurersv, String datersv, double impot, double total, double mtotal) {
        this.id = id;
        this.qte = qte;
        this.periode = periode;
        this.prix = prix;
        this.model = model;
        this.heurersv = heurersv;
        this.datersv = datersv;
        this.impot = impot;
        this.total = total;
        this.mtotal = mtotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public int getPeriode() {
        return periode;
    }

    public void setPeriode(int periode) {
        this.periode = periode;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getHeurersv() {
        return heurersv;
    }

    public void setHeurersv(String heurersv) {
        this.heurersv = heurersv;
    }

    public String getDatersv() {
        return datersv;
    }

    public void setDatersv(String datersv) {
        this.datersv = datersv;
    }

    public double getImpot() {
        return impot;
    }

    public void setImpot(double impot) {
        this.impot = impot;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getMtotal() {
        return mtotal;
    }

    public void setMtotal(double mtotal) {
        this.mtotal = mtotal;
    }
}

