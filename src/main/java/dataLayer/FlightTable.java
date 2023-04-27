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
    public static String dbs_where = " WHERE ID = ?";
    public static String query = "";

    public FlightTable() {
        DBTable.tableName = "flight";

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
        query = DBTable.dbq_select + DBTable.tableName;

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
    public Flight getRecordById(TableId id) {
        return null;
    }

    @Override
    public boolean insertNewRecord(Flight newRecord) {
        return false;
    }

    @Override
    public boolean deleteRecordById(String id) {
        return false;
    }
}
