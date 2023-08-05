package data.factory;

import data.dao.impl.CustomerTable;
import data.dao.CustomerDAO;

public class CustomerDAOFactory {
    public static CustomerDAO createCustomerDAO() {
        return new CustomerTable();
    }
}
