package databaseConnection;

import commonStructures.AirportCode;
import flight.Airport;
import flight.Coordinate;
import flight.Location;
import utilities.Converter;

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

public class DBAccess implements DAO {
    public static final String DBT_AIRPORT = "airport";
    public static final String DBT_FLIGHT = "flight";
    public static final String DBQ_SELECT_ALL_AIRPORTS = "SELECT * FROM " + DBT_AIRPORT;
    public static final String DBQ_SELECT_AIRPORT_BY_CODE = "SELECT * FROM " + DBT_AIRPORT + " WHERE AirportCode = ?";
    private static String host;
    private static String username;
    private static String password;

    public DBAccess() {
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
    public List<Airport> getAllAirPorts() {
        List<Airport> allAirports = new LinkedList<>();

        try (final Connection con = getConnection(host, username, password);
             PreparedStatement SELECT_ALL = con.prepareStatement(DBQ_SELECT_ALL_AIRPORTS)) {
            final ResultSet resultSet = SELECT_ALL.executeQuery();

            while (resultSet.next()) {
                final AirportCode code = valueOf(resultSet.getString("AirportCode"));
                final Coordinate coordinate = jsonToCoordinate(resultSet.getString("Coordinate"));
                final Location location = jsonToLocation(resultSet.getString("Location"));
                allAirports.add(new Airport(code, coordinate, location));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allAirports;
    }

    @Override
    public Airport getAirPortByCode(AirportCode airportCode) {
        Airport airportByCode;

        try (final Connection con = getConnection(host, username, password);
             PreparedStatement SELECT_BY_CODE = con.prepareStatement(DBQ_SELECT_AIRPORT_BY_CODE)) {

            SELECT_BY_CODE.setString(1, airportCode.toString());
            final ResultSet resultSet = SELECT_BY_CODE.executeQuery();

            resultSet.next();
            final AirportCode code = valueOf(resultSet.getString("AirportCode"));
            final Coordinate coordinate = jsonToCoordinate(resultSet.getString("Coordinate"));
            final Location location = jsonToLocation(resultSet.getString("Location"));

            airportByCode = new Airport(code, coordinate, location);
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return airportByCode;
    }

    @Override
    public Location getAirportLocationByCode(AirportCode airportCode) {
        return getAirPortByCode(airportCode).getLocation();
    }

    @Override
    public Coordinate getAirportCoordinateByCode(AirportCode airportCode) {
        return getAirPortByCode(airportCode).getCoordinate();
    }
}