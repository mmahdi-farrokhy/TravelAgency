package datalayer.factories;

import datalayer.FlightTable;
import datalayer.daos.FlightDAO;

public class FlightDAOFactory {
    public static FlightDAO createCustomerDAO() {
        return new FlightTable();
    }
}
