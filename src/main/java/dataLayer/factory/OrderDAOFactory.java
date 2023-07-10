package dataLayer.factory;

import dataLayer.OrderTable;
import dataLayer.dao.OrderDAO;

public class OrderDAOFactory {
    public static OrderDAO createCustomerDAO() {
        return new OrderTable();
    }
}
