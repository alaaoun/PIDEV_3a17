package services;

import models.trotinette;

import java.sql.SQLException;
import java.util.List;

public interface Iservice <t>{
    void ajouter(trotinette trotinette) throws SQLException;
    void modifer(trotinette trotinette, int id_trotinette ) throws SQLException;



    void supprimer(int id_trotinette ) throws SQLException;
    List <t> recuperer() throws SQLException;
}
