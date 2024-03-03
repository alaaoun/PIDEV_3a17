package services;

import models.reservation;

import java.sql.SQLException;
import java.util.List;

public interface Iservice1<T> {

    void ajouter(T t) throws SQLException;

    void modifier(T t) throws SQLException;

    void supprimer(int id) throws SQLException;

    List<T> recuperer() throws SQLException;


}
