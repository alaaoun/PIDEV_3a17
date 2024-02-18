package tests;


import models.trotinette;
import models.station;
import services.stationservice;
import services.trotinetteservice;
import utils.MYdatabase;

import java.sql.SQLException;

public class main {
    public static void main(String[] args) throws SQLException {
         trotinetteservice ts = new trotinetteservice();


        { ts.ajouter(new trotinette(45,13,"de","ddcd",4));
;
        }
    }
}