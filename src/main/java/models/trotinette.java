package models;

public class trotinette {

   private int id_trotinette ,vitesse,charge,id_station;
  private   String couleur,dispo;

    public trotinette() {
    }

    public trotinette(int vitesse, int charge, String couleur,String dispo,int id_station) {
        this.vitesse = vitesse;
        this.charge = charge;
        this.couleur = couleur;
        this.dispo = dispo;
        this.id_station= id_station;
    }

    public trotinette(int id_trotinette, int vitesse, int charge, String couleur, String dispo,int id_station) {
        this.id_trotinette = id_trotinette;
        this.vitesse = vitesse;
        this.charge = charge;
        this.couleur = couleur;
        this.dispo = dispo;
        this.id_station=id_station;
    }

    public int getId_trotinette() {
        return id_trotinette;
    }

    public void setId_trotinette(int id_trotinette) {
        this.id_trotinette = id_trotinette;
    }
public int getId_station(){
        return id_station;
}
public void setId_station(int id_station){
        this.id_station=id_station;
}
    public int getVitesse() {
        return vitesse;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getDispo() {
        return dispo;
    }

    public void setDispo(String dispo) {
        this.dispo = dispo;
    }

    @Override
    public String toString() {
        return "trotinette{" +
                "id_trotinette=" + id_trotinette +
                ", vitesse=" + vitesse +
                ", charge=" + charge +
                ", couleur='" + couleur + '\'' +
                ", dispo='" + dispo + '\'' +
                ", idStation=" + id_station+
                '}';
    }
}
