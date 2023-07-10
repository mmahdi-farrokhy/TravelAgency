package dataLayer.factory;

import dataLayer.AirportTable;
import dataLayer.dao.AirportDAO;

public class AirportDAOFactory {
    public static AirportDAO createAirportDAO() {
        return new AirportTable();
    }
}