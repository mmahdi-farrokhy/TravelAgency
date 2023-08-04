package data.factory;

import data.dao.OrderDAO;
import data.daoImpl.OrderTable;

public class OrderDAOFactory {
    public static OrderDAO createOrderDAO() {
        return new OrderTable();
    }
}
