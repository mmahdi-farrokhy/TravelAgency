package datalayer.factories;

import datalayer.AirportTable;
import datalayer.daos.AirportDAO;

public class AirportDAOFactory {
    public static AirportDAO createAirportDAO() {
        return new AirportTable();
    }
}