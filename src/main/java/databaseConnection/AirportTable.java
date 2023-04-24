package databaseConnection;

import commonStructures.AirportCode;
import commonStructures.TableId;
import flight.Airport;
import flight.Coordinate;
import flight.Location;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static commonStructures.AirportCode.valueOf;
import static java.sql.DriverManager.getConnection;
import static utilities.Converter.jsonToCoordinate;
import static utilities.Converter.jsonToLocation;

public class AirportTable implements DBAccess {
    public static final String DBT_AIRPORT = "airport";
    public static final String DBQ_SELECT = "SELECT * FROM ";
    public static String DBS_WHERE = " WHERE AirportCode = ?";
    public static String QUERY = "";
    private static String host;
    private static String username;
    private static String password;

    public AirportTable() {
        try (InputStream configFile = Files.newInputStream(Paths.get("db-config.properties"))) {
            final Properties properties = new Properties();
            properties.load(configFile);
            host = properties.get("host").toString();
            username = properties.get("user").toString();
            password = properties.get("pass").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Airport> getAllRecords() {
        List<Airport> allAirports = new LinkedList<>();
        QUERY = DBQ_SELECT + DBT_AIRPORT;

        try (final Connection con = getConnection(host, username, password);
             PreparedStatement SELECT_ALL = con.prepareStatement(QUERY)) {

            final ResultSet resultSet = SELECT_ALL.executeQuery();
            while (resultSet.next()) {
                allAirports.add(generateAirportFromResultSet(resultSet));
            }

            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allAirports;
    }

    @Override
    public Airport getRecordById(TableId id) {
        Airport airportByCode;
        QUERY = DBQ_SELECT + DBT_AIRPORT + DBS_WHERE;

        try (final Connection con = getConnection(host, username, password);
             PreparedStatement SELECT_BY_CODE = con.prepareStatement(QUERY)) {

            SELECT_BY_CODE.setString(1, id.toString());
            final ResultSet resultSet = SELECT_BY_CODE.executeQuery();
            resultSet.next();
            airportByCode = generateAirportFromResultSet(resultSet);
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return airportByCode;
    }

    private static Airport generateAirportFromResultSet(@NotNull ResultSet resultSet) {
        Airport airportByCode;

        try {
            final AirportCode code = valueOf(resultSet.getString("AirportCode"));
            final Coordinate coordinate = jsonToCoordinate(resultSet.getString("Coordinate"));
            final Location location = jsonToLocation(resultSet.getString("Location"));
            airportByCode = new Airport(code, coordinate, location);
        } catch (IllegalArgumentException | SQLException e) {
            throw new RuntimeException(e);
        }

        return airportByCode;
    }

    @Override
    public Location getRecordLocationById(TableId airportCode) {
        return getRecordById(airportCode).getLocation();
    }

    @Override
    public Coordinate getRecordCoordinateById(TableId airportCode) {
        return getRecordById(airportCode).getCoordinate();
    }
}