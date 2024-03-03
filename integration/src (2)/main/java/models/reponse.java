package models;

import javafx.scene.control.TextField;

import java.util.Date;

public class reponse {
    private int idrep,idrec;
    private String response;
    private Date daterep;
    private String emailrep;

    public  reponse(){}
    public reponse(int idrep, String emailrep, Date daterep, String response,int idrec) {
        this.idrep = idrep;
        this.response = response;
        this.daterep = daterep;
        this.emailrep = emailrep;
        this.idrec = idrec;
    }
    public reponse(String emailrep, Date daterep, String response,int idrec) {
        this.response = response;
        this.daterep = daterep;
        this.emailrep = emailrep;
        this.idrec = idrec;
    }
    public int getIdrep() {
        return idrep;
    }

    public void setIdrep(int idrep) {
        this.idrep = idrep;
    }

    public int getIdRec() {
        return idrec;
    }

    public void setIdRec(int idrec) {
        this.idrec = idrec;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getDaterep() {
        return daterep;
    }

    public void setDaterep(Date daterep) {
        this.daterep = daterep;
    }

    public String getEmailrep() {
        return emailrep;
    }

    public void setEmailrep(String emailrep) {
        this.emailrep = emailrep;
    }




    @Override
    public String toString() {
        return "reponse{" +
                "idrep=" + idrep +
                ", response='" + response + '\'' +
                ", daterep=" + daterep +
                ", emailrep='" + emailrep + '\'' +
                '}';
    }

    public void addResponseWithForeignKey(TextField idrep, TextField emailrep, TextField daterep, TextField response) {
    }
}
