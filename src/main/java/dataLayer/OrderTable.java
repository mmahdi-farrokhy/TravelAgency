package dataLayer;

import model.Flight;
import model.Customer;
import model.Order;
import model.submodel.Price;
import commonStructures.CurrencyType;
import model.DBTable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static java.lang.Integer.parseInt;
import static java.sql.DriverManager.getConnection;

public class OrderTable implements DBChange<Order> {

    public OrderTable() {
        try (InputStream configFile = Files.newInputStream(Paths.get("db-config.properties"))) {
            final Properties properties = new Properties();
            properties.load(configFile);
            DBTable.host = properties.get("host").toString();
            DBTable.username = properties.get("user").toString();
            DBTable.password = properties.get("pass").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getAllRecords() {
        List<Order> allOrders = new LinkedList<>();
        DBTable.tableName = "orders";
        DBTable.query = DBAccess.DBQ_SELECT + DBTable.tableName;

        try (final Connection con = getConnection(DBTable.host, DBTable.username, DBTable.password);
             PreparedStatement SELECT_ALL = con.prepareStatement(DBTable.query)) {

            final ResultSet resultSet = SELECT_ALL.executeQuery();
            while (resultSet.next()) {
                allOrders.add(generateRecordFromResultSet(resultSet));
            }

            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allOrders;
    }

    @Override
    public Order generateRecordFromResultSet(ResultSet resultSet) {
        Order orderById = new Order();

        try {
            final String id = resultSet.getString("ID");
            final int quantity = resultSet.getInt("Quantity");
            final Price price = new Price(resultSet.getDouble("Price"), CurrencyType.USD);
            final LocalDateTime registrationTime = resultSet.getObject("RegistrationTime", LocalDateTime.class);
            final Customer customerInfo = new Customer(resultSet.getString("CustomerNationalCode"));
            final Flight flight = new Flight(resultSet.getString("FlightID"));

            orderById.setId(id);
            orderById.setQuantity(quantity);
            orderById.setPrice(price);
            orderById.setRegistrationTime(registrationTime);
            orderById.setCustomerInfo(customerInfo);
            orderById.setFlight(flight);

        } catch (IllegalArgumentException | SQLException e) {
            throw new RuntimeException(e);
        }

        return orderById;

    }

    @Override
    public Order getRecordById(String id) {
        Order orderById;
        DBTable.tableName = "orders";
        DBTable.query = DBAccess.DBQ_SELECT + DBTable.tableName + DBS_WHERE.replace("id", "ID");

        try (final Connection con = getConnection(DBTable.host, DBTable.username, DBTable.password);
             PreparedStatement SELECT_BY_CODE = con.prepareStatement(DBTable.query)) {
            SELECT_BY_CODE.setInt(1, parseInt(id));
            final ResultSet resultSet = SELECT_BY_CODE.executeQuery();
            resultSet.next();
            orderById = generateRecordFromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orderById;
    }

    @Override
    public boolean insertNewRecord(Order newRecord) {
        DBTable.tableName = "orders";
        DBTable.query = DBQ_INSERT + DBTable.tableName + " (Quantity, Price, RegistrationTime, CustomerNationalCode, FlightID) VALUES (?, ?, ?, ?, ?)";
        boolean recordInserted = false;

        try (final Connection con = getConnection(DBTable.host, DBTable.username, DBTable.password);
             PreparedStatement INSERT = con.prepareStatement(DBTable.query)) {
            List<Order> orderList = getAllRecords();
            if (!orderList.contains(newRecord)) {
                INSERT.setInt(1, newRecord.getQuantity());
                INSERT.setDouble(2, newRecord.getPrice().getAmount());
                INSERT.setString(3, newRecord.getRegistrationTime().toString());
                INSERT.setString(4, newRecord.getCustomerInfo().getNationalCode());
                INSERT.setInt(5, parseInt(newRecord.getFlight().getId()));
                INSERT.executeUpdate();
                recordInserted = true;
            }
            } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return recordInserted;

    }

    @Override
    public void deleteRecordById(String id) {
        DBTable.tableName = "orders";
        DBTable.query = DBQ_DELETE + DBTable.tableName + DBS_WHERE.replace("id", "ID");

        try (final Connection con = getConnection(DBTable.host, DBTable.username, DBTable.password);
             PreparedStatement DELETE_BY_ID = con.prepareStatement(DBTable.query)) {
            DELETE_BY_ID.setInt(1, parseInt(id));
            DELETE_BY_ID.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
