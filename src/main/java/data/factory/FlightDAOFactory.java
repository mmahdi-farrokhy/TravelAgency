package data.factory;

import data.dao.impl.FlightTable;
import data.dao.FlightDAO;

public class FlightDAOFactory {
    public static FlightDAO createFlightDAO() {
        return new FlightTable();
    }
}
