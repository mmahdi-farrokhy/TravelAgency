package data.dao.factory;

import data.dao.OrderDAO;
import data.dao.impl.OrderTable;

public class OrderDAOFactory {
    public static OrderDAO createOrderDAO() {
        return new OrderTable();
    }
}
