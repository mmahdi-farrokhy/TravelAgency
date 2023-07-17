package datalayer.factories;

import datalayer.OrderTable;
import datalayer.daos.OrderDAO;

public class OrderDAOFactory {
    public static OrderDAO createCustomerDAO() {
        return new OrderTable();
    }
}
