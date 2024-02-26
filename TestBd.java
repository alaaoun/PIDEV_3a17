package test;

import models.User;
import services.UserService;
import utils.MyDataBase;

import java.sql.*;

public class TestBd {

    public static void main(String [] args){


        MyDataBase d = MyDataBase.getInstance();

        UserService ps = new UserService();
        try {
            ps.ajouter(new User("sqdqs","hachicha","hachicha","hachicha","hachicha"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {ps.supprimer(3);
        }catch (SQLException s){
            System.out.println("(no pasaraan)");
        }
try {
    System.out.println(ps.afficher());
}catch (SQLException s){
    System.out.println(s.getMessage());
}

    }
}
