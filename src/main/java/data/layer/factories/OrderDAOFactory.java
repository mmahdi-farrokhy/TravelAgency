package data.layer.factories;

import data.layer.dao.implementers.OrderTable;
import data.layer.daos.OrderDAO;

public class OrderDAOFactory {
    public static OrderDAO createCustomerDAO() {
        return new OrderTable();
    }
}
