package models;




public class User {

    private int id;
    private String nom;

    private String mail;

    public User(int id, String nom, String num_tel) {
        this.id = id;
        this.nom = nom;
        this.num_tel = num_tel;
    }

    private String num_tel;

    private String adresse;
    private String mdp;
    private String role_id;
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", mail='" + mail + '\'' +
                ", num_tel='" + num_tel + '\'' +
                ", adresse='" + adresse + '\'' +
                ", mdp='" + mdp + '\'' +
                ", role='" + role_id + '\'' +
                '}';
    }


    public User(String nom, String mail ,String num_tel, String adresse, String mdp) {
        this.nom = nom;
        this.mail = mail;
        this.num_tel = num_tel;
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

    public User(String nom, String mail, String num_tel, String adresse, String mdp,String role_id) {
        this.nom = nom;
        this.mail = mail;
        this.num_tel = num_tel;
        this.adresse = adresse;
        this.mdp = mdp;
        this.role_id = role_id;
    }

    public User(int id, String nom ,String mail, String num_tel, String adresse, String mdp,String role_id) {
        this.id = id;
        this.nom = nom;
        this.mail = mail;
        this.num_tel = num_tel;
        this.adresse = adresse;
        this.mdp = mdp;
        this.role_id = role_id;
    }


    public String getMail() {
        return mail;
    }

    public String getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
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


    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public void setId(int id) {
    }
}


