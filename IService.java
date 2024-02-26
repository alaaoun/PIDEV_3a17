package services;

import models.User;

import java.sql.SQLException;
import java.util.List;

public interface IService <T> {

    public void ajouter(T t) throws SQLException;

    void ajouteruser(User user) throws SQLException;


    void ajouteradmin(User user) throws SQLException;

    void modifierUser(User user) throws SQLException;

    public void supprimer(int id) throws SQLException;
    public List<T> afficher() throws SQLException;
}
