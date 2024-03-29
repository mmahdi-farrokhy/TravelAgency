package data.dao.impl;

import data.crud.DBAccess;
import data.dao.FlightDAO;
import dto.FlightDTO;
import model.Airport;
import model.Flight;
import commonStructures.AirportCode;
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

import static java.sql.DriverManager.getConnection;

public class FlightTable implements FlightDAO {

    public FlightTable() {
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
    public List<FlightDTO> getAllRecords() {
        List<FlightDTO> allFlights = new LinkedList<>();
        DBTable.tableName = "flights";
        DBTable.query = DBAccess.DBQ_SELECT + DBTable.tableName;

        try (final Connection con = getConnection(DBTable.host, DBTable.username, DBTable.password);
             PreparedStatement SELECT_ALL = con.prepareStatement(DBTable.query)) {

            final ResultSet resultSet = SELECT_ALL.executeQuery();
            while (resultSet.next()) {
                allFlights.add(generateRecordFromResultSet(resultSet));
            }

            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allFlights;
    }

    @Override
    public FlightDTO generateRecordFromResultSet(ResultSet resultSet) {
        FlightDTO flightDataById = new FlightDTO();

        try {
            final String id = resultSet.getString("ID");
            final LocalDateTime departureTime = resultSet.getObject("DepartureTime", LocalDateTime.class);
            final Airport originAirport = new Airport(AirportCode.valueOf(resultSet.getString("OriginAirportCode")));
            final Airport destinationAirport = new Airport(AirportCode.valueOf(resultSet.getString("DestinationAirportCode")));

            flightDataById.setId(id);
            flightDataById.setDepartureTime(departureTime);
            flightDataById.setOriginAirport(originAirport);
            flightDataById.setDestinationAirport(destinationAirport);
        } catch (IllegalArgumentException | SQLException e) {
            throw new RuntimeException(e);
        }

        return flightDataById;
    }

    @Override
    public FlightDTO getRecordById(String id) {
        FlightDTO flightById;
        DBTable.tableName = "flights";
        DBTable.query = DBAccess.DBQ_SELECT + DBTable.tableName + DBS_WHERE.replace("id", "ID");

        try (final Connection con = getConnection(DBTable.host, DBTable.username, DBTable.password);
             PreparedStatement SELECT_BY_CODE = con.prepareStatement(DBTable.query)) {
            SELECT_BY_CODE.setInt(1, Integer.parseInt(id));
            final ResultSet resultSet = SELECT_BY_CODE.executeQuery();
            resultSet.next();
            flightById = generateRecordFromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return flightById;
    }

    @Override
    public boolean insertNewRecord(FlightDTO newRecord) {
        DBTable.tableName = "flights";
        DBTable.query = DBQ_INSERT + DBTable.tableName + " (DepartureTime, OriginAirportCode, DestinationAirportCode) VALUES (?, ?, ?)";
        boolean recordInserted = false;

        try (final Connection con = getConnection(DBTable.host, DBTable.username, DBTable.password);
             PreparedStatement INSERT = con.prepareStatement(DBTable.query)) {
            List<FlightDTO> flightList = getAllRecords();
            if (!flightList.contains(newRecord)) {
                INSERT.setString(1, newRecord.getDepartureTime().toString());
                INSERT.setString(2, newRecord.getOriginAirport().getCode().toString());
                INSERT.setString(3, newRecord.getDestinationAirport().getCode().toString());
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
        DBTable.tableName = "flights";
        DBTable.query = DBQ_DELETE + DBTable.tableName + DBS_WHERE.replace("id", "ID");

        try (final Connection con = getConnection(DBTable.host, DBTable.username, DBTable.password);
             PreparedStatement DELETE_BY_ID = con.prepareStatement(DBTable.query)) {
            DELETE_BY_ID.setInt(1, Integer.parseInt(id));
            DELETE_BY_ID.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
