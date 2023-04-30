package dataLayer;

import applicationLayer.Airport;
import applicationLayer.Coordinate;
import applicationLayer.Flight;
import applicationLayer.Location;
import commonStructures.AirportCode;
import commonStructures.DBTable;
import commonStructures.TableId;

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

import static commonStructures.AirportCode.valueOf;
import static java.sql.DriverManager.getConnection;
import static utilities.Converter.jsonToCoordinate;
import static utilities.Converter.jsonToLocation;

public class FlightTable implements DBUpdate<Flight> {
    public static String query = "";

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
    public List<Flight> getAllRecords() {
        List<Flight> allFlights = new LinkedList<>();
        DBTable.tableName = "flights";
        query = DBAccess.dbq_select + DBTable.tableName;

        try (final Connection con = getConnection(DBTable.host, DBTable.username, DBTable.password);
             PreparedStatement SELECT_ALL = con.prepareStatement(query)) {

            final ResultSet resultSet = SELECT_ALL.executeQuery();
            while (resultSet.next()) {
                allFlights.add(generateFlightFromResultSet(resultSet));
            }

            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allFlights;
    }

    private Flight generateFlightFromResultSet(ResultSet resultSet) {
        Flight flightById = new Flight();

        try {
            final String id = resultSet.getString("ID");
            final LocalDateTime departureTime = resultSet.getObject("DepartureTime", LocalDateTime.class);
            final Airport originAirport = new Airport(AirportCode.valueOf(resultSet.getString("OriginAirportCode")));
            final Airport destinationAirport = new Airport(AirportCode.valueOf(resultSet.getString("DestinationAirportCode")));

            flightById.setId(id);
            flightById.setDepartureTime(departureTime);
            flightById.setOriginAirport(originAirport);
            flightById.setDestinationAirport(destinationAirport);
        } catch (IllegalArgumentException | SQLException e) {
            throw new RuntimeException(e);
        }

        return flightById;
    }

    @Override
    public Flight getRecordById(String id) {
        Flight flightById;
        DBTable.tableName = "flights";
        query = DBAccess.dbq_select + DBTable.tableName + dbs_where.replace("id", "ID");

        try (final Connection con = getConnection(DBTable.host, DBTable.username, DBTable.password);
             PreparedStatement SELECT_BY_CODE = con.prepareStatement(query)) {
            SELECT_BY_CODE.setInt(1, Integer.valueOf(id));
            final ResultSet resultSet = SELECT_BY_CODE.executeQuery();
            resultSet.next();
            flightById = generateFlightFromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return flightById;
    }

    @Override
    public boolean insertNewRecord(Flight newRecord) {
        DBTable.tableName = "flights";
        query = dbq_insert + DBTable.tableName + " (DepartureTime, OriginAirportCode, DestinationAirportCode) VALUES (?, ?, ?)";
        boolean recordInserted = false;

        try (final Connection con = getConnection(DBTable.host, DBTable.username, DBTable.password);
             PreparedStatement INSERT = con.prepareStatement(query)) {
            List<Flight> flightList = getAllRecords();
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
        query = dbq_delete + DBTable.tableName + dbs_where.replace("id", "ID");

        try (final Connection con = getConnection(DBTable.host, DBTable.username, DBTable.password);
             PreparedStatement DELETE_BY_ID = con.prepareStatement(query)) {
            DELETE_BY_ID.setInt(1, Integer.valueOf(id));
            DELETE_BY_ID.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
