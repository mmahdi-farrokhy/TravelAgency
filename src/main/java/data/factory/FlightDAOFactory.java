package data.factory;

import data.daoImpl.FlightTable;
import data.dao.FlightDAO;

public class FlightDAOFactory {
    public static FlightDAO createCustomerDAO() {
        return new FlightTable();
    }
}
