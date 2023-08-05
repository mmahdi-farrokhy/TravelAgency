package data.factory;

import data.dao.impl.AirportTable;
import data.dao.AirportDAO;

public class AirportDAOFactory {
    public static AirportDAO createAirportDAO() {
        return new AirportTable();
    }
}