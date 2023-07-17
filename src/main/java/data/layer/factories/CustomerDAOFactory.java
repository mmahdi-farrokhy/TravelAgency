package data.layer.factories;

import data.layer.dao.implementers.CustomerTable;
import data.layer.daos.CustomerDAO;

public class CustomerDAOFactory {
    public static CustomerDAO createCustomerDAO() {
        return new CustomerTable();
    }
}
