package dataLayer;

import commonStructures.AirportCode;
import commonStructures.TableId;
import applicationLayer.Airport;
import applicationLayer.Coordinate;
import applicationLayer.Location;
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

public class AirportTable extends DBTable implements DBAccess<Airport> {
    public static String dbs_where = " WHERE AirportCode = ?";
    public static String query = "";

    public AirportTable() {
        tableName = "airport";

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
        query = dbq_select + tableName;

        try (final Connection con = getConnection(host, username, password);
             PreparedStatement SELECT_ALL = con.prepareStatement(query)) {

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
        query = dbq_select + tableName + dbs_where;

        try (final Connection con = getConnection(host, username, password);
             PreparedStatement SELECT_BY_CODE = con.prepareStatement(query)) {

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
            final AirportCode airportCode = valueOf(resultSet.getString("AirportCode"));
            final Coordinate coordinate = jsonToCoordinate(resultSet.getString("Coordinate"));
            final Location location = jsonToLocation(resultSet.getString("Location"));

            airportByCode = new Airport(airportCode, coordinate, location);
        } catch (IllegalArgumentException | SQLException e) {
            throw new RuntimeException(e);
        }

        return airportByCode;
    }

    public Location getAirportLocationById(TableId airportCode) {
        return getRecordById(airportCode).getLocation();
    }

    public Coordinate getAirportCoordinateById(TableId airportCode) {
        return getRecordById(airportCode).getCoordinate();
    }
}