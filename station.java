package models;

public class station {
    private String lieu ,name ,etat;
    private int id_station;

    public station(String lieu, String name, String etat, int id_station) {
        this.lieu = lieu;
        this.name = name;
        this.etat = etat;
        this.id_station = id_station;
    }

    public station(String lieu, String name, String etat) {
        this.lieu = lieu;
        this.name = name;
        this.etat = etat;
    }

    public station() {
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getId_station() {
        return id_station;
    }

    public void setId_station(int id_station) {
        this.id_station = id_station;
    }

    @Override
    public String toString() {
        return "station{" +
                "lieu='" + lieu + '\'' +
                ", name='" + name + '\'' +
                ", etat='" + etat + '\'' +
                ", id_station=" + id_station +
                '}';
    }
}
