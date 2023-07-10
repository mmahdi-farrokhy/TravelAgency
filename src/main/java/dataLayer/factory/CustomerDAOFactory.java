package dataLayer.factory;

import dataLayer.CustomerTable;
import dataLayer.dao.CustomerDAO;

public class CustomerDAOFactory {
    public static CustomerDAO createCustomerDAO() {
        return new CustomerTable();
    }
}
