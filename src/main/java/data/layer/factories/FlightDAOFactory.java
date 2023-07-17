package data.layer.factories;

import data.layer.dao.implementers.FlightTable;
import data.layer.daos.FlightDAO;

public class FlightDAOFactory {
    public static FlightDAO createCustomerDAO() {
        return new FlightTable();
    }
}
