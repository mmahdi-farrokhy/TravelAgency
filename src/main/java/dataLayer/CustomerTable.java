package dataLayer;

import commonStructures.City;
import dataLayer.dao.CustomerDAO;
import model.DBTable;
import model.Customer;
import model.submodel.Address;
import model.submodel.FullName;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;
import static utilities.ConversionUtils.jsonToProperty;

public class CustomerTable implements CustomerDAO {

    public CustomerTable() {
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
    public List<Customer> getAllRecords() {
        List<Customer> allCustomers = new LinkedList<>();
        DBTable.tableName = "customers";
        DBTable.query = DBAccess.DBQ_SELECT + DBTable.tableName;

        try (final Connection con = getConnection(DBTable.host, DBTable.username, DBTable.password);
             PreparedStatement SELECT_ALL = con.prepareStatement(DBTable.query)) {
            final ResultSet resultSet = SELECT_ALL.executeQuery();
            while (resultSet.next()) {
                allCustomers.add(generateRecordFromResultSet(resultSet));
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allCustomers;
    }

    @Override
    public Customer generateRecordFromResultSet(ResultSet resultSet) {
        Customer customerById = new Customer();

        try {
            final String nationalCode = resultSet.getString("NationalCode");
            final FullName fullName = jsonToProperty(resultSet.getString("FullName"), new FullName("", ""));
            final LocalDate birthDate = resultSet.getObject("BirthDate", LocalDate.class);
            final Address address = jsonToProperty(resultSet.getString("Address"), new Address(City.NONE, "", ""));
            final String phoneNumber = resultSet.getString("PhoneNumber");
            final String email = resultSet.getString("Email");
            final String password = resultSet.getString("Password");

            customerById.setNationalCode(nationalCode);
            customerById.setFullName(fullName);
            customerById.setBirthDate(birthDate);
            customerById.setAddress(address);
            customerById.setPhoneNumber(phoneNumber);
            customerById.setEmail(email);
            customerById.setPassword(password);
        } catch (IllegalArgumentException | SQLException e) {
            throw new RuntimeException(e);
        }

        return customerById;

    }

    @Override
    public Customer getRecordById(String id) {
        Customer customerByNationalCode;
        DBTable.tableName = "customers";
        DBTable.query = DBAccess.DBQ_SELECT + DBTable.tableName + DBS_WHERE.replace("id", "NationalCode");

        try (final Connection con = getConnection(DBTable.host, DBTable.username, DBTable.password);
             PreparedStatement SELECT_BY_CODE = con.prepareStatement(DBTable.query)) {
            SELECT_BY_CODE.setInt(1, Integer.parseInt(id));
            final ResultSet resultSet = SELECT_BY_CODE.executeQuery();
            resultSet.next();
            customerByNationalCode = generateRecordFromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customerByNationalCode;
    }

    @Override
    public boolean insertNewRecord(Customer newRecord) {
        DBTable.tableName = "customers";
        DBTable.query = DBQ_INSERT + DBTable.tableName + " (NationalCode, FullName, BirthDate, Address, PhoneNumber, Password, Email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        boolean recordInserted = false;

        try (final Connection con = getConnection(DBTable.host, DBTable.username, DBTable.password);
             PreparedStatement INSERT = con.prepareStatement(DBTable.query)) {
            List<Customer> customerList = getAllRecords();
            if (!customerList.contains(newRecord)) {
                INSERT.setString(1, newRecord.getNationalCode());
                INSERT.setString(2, newRecord.getFullName().toJson());
                INSERT.setString(3, newRecord.getBirthDate().toString());
                INSERT.setString(4, newRecord.getAddress().toJson());
                INSERT.setString(5, newRecord.getPhoneNumber());
                INSERT.setString(6, newRecord.getPassword());
                INSERT.setString(7, newRecord.getEmail());
                INSERT.executeUpdate();
                recordInserted = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return recordInserted;

    }

    @Override
    public void deleteRecordById(String id) {
        DBTable.tableName = "customers";
        DBTable.query = DBQ_DELETE + DBTable.tableName + DBS_WHERE.replace("id", "NationalCode") + " LIMIT 1";

        try (final Connection con = getConnection(DBTable.host, DBTable.username, DBTable.password);
             PreparedStatement DELETE_BY_ID = con.prepareStatement(DBTable.query)) {
            DELETE_BY_ID.setInt(1, Integer.parseInt(id));
            DELETE_BY_ID.execute();
            var t=0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateRecord(Customer newRecord) {
        boolean recordUpdated = false;
        DBTable.tableName = "customers";
        String customersColumns = "FullName = ?, BirthDate = ?, Address = ?, PhoneNumber = ?, Email = ?, Password = ?";
        DBTable.query = DBUpdate.DBQ_UPDATE.replace("table", DBTable.tableName).replace("columnValuePairs", customersColumns) + DBS_WHERE.replace("id", "NationalCode");

        try (final Connection con = getConnection(DBTable.host, DBTable.username, DBTable.password);
             PreparedStatement UPDATE = con.prepareStatement(DBTable.query)) {
            List<Customer> customerList = getAllRecords();
            if (customerList.contains(newRecord)) {
                UPDATE.setString(1, newRecord.getFullName().toJson());
                UPDATE.setString(2, newRecord.getBirthDate().toString());
                UPDATE.setString(3, newRecord.getAddress().toJson());
                UPDATE.setString(4, newRecord.getPhoneNumber());
                UPDATE.setString(5, newRecord.getEmail());
                UPDATE.setString(6, newRecord.getPassword());
                UPDATE.setString(7, newRecord.getNationalCode());
                UPDATE.executeUpdate();
                recordUpdated = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return recordUpdated;
    }
}
