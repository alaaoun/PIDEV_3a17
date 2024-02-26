package models;




public class User {

    private int id;
    private String nom;

    private String num_tel;

    private String mail;
    private String adresse;
    private String mdp;
    private String role;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", num_tel='" + num_tel + '\'' +
                ", mail='" + mail + '\'' +
                ", adresse='" + adresse + '\'' +
                ", mdp='" + mdp + '\'' +
                ", role='" + role + '\'' +
                '}';
    }


    public User(String nom, String num_tel, String mail, String adresse, String mdp) {
        this.nom = nom;
        this.num_tel = num_tel;
        this.mail = mail;
        this.adresse = adresse;
        this.mdp = mdp;
    }

    public int getId() {
        return id;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public User(int id) {
        this.id = id;
    }

    public User(String nom, String num_tel, String mail, String adresse, String mdp,String role) {
        this.nom = nom;
        this.num_tel = num_tel;
        this.mail = mail;
        this.adresse = adresse;
        this.mdp = mdp;
        this.role = role;
    }

    public User(int id, String nom, String num_tel, String mail, String adresse, String mdp,String role) {
        this.id = id;
        this.nom = nom;
        this.num_tel = num_tel;
        this.mail = mail;
        this.adresse = adresse;
        this.mdp = mdp;
        this.role = role;
    }


    public String getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public User(){}


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setId(int id) {
    }
}


