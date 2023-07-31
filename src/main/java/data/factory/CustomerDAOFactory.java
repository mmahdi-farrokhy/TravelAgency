package data.factory;

import data.daoImpl.CustomerTable;
import data.dao.CustomerDAO;

public class CustomerDAOFactory {
    public static CustomerDAO createCustomerDAO() {
        return new CustomerTable();
    }
}
