package datalayer.factories;

import datalayer.CustomerTable;
import datalayer.daos.CustomerDAO;

public class CustomerDAOFactory {
    public static CustomerDAO createCustomerDAO() {
        return new CustomerTable();
    }
}
