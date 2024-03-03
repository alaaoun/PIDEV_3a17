package services;

import models.station;
import models.trotinette;

import java.sql.SQLException;
import java.util.List;

public interface Sservice <s>{
    void ajouter(station station) throws SQLException;
    void modifer(station station, int id_station ) throws SQLException;



    void supprimer(int id_station ) throws SQLException;
    List <s> recupererr() throws SQLException;
}
