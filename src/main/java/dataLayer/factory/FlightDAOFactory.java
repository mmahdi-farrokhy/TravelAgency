package dataLayer.factory;

import dataLayer.FlightTable;
import dataLayer.dao.FlightDAO;

public class FlightDAOFactory {
    public static FlightDAO createCustomerDAO() {
        return new FlightTable();
    }
}
