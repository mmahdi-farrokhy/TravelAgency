package data.layer.factories;

import data.layer.dao.implementers.AirportTable;
import data.layer.daos.AirportDAO;

public class AirportDAOFactory {
    public static AirportDAO createAirportDAO() {
        return new AirportTable();
    }
}