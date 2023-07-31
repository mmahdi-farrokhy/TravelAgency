package data.factory;

import data.daoImpl.AirportTable;
import data.dao.AirportDAO;

public class AirportDAOFactory {
    public static AirportDAO createAirportDAO() {
        return new AirportTable();
    }
}