package dataLayer;

import commonStructures.AirportCode;
import commonStructures.DBTable;
import model.Airport;
import model.submodel.Coordinate;
import model.submodel.Location;

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
import static utilities.ConversionUtils.*;

public class AirportTable implements DBAccess<Airport> {
    public AirportTable() {
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
    public List<Airport> getAllRecords() {
        List<Airport> allAirports = new LinkedList<>();
        DBTable.tableName = "airports";
        DBTable.query = DBAccess.DBQ_SELECT + DBTable.tableName;

        try (final Connection con = getConnection(DBTable.host, DBTable.username, DBTable.password);
             PreparedStatement SELECT_ALL = con.prepareStatement(DBTable.query)) {

            final ResultSet resultSet = SELECT_ALL.executeQuery();
            while (resultSet.next()) {
                allAirports.add(generateRecordFromResultSet(resultSet));
            }

            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allAirports;
    }

    @Override
    public Airport getRecordById(String id) {
        Airport airportByCode;
        DBTable.tableName = "airports";
        DBTable.query = DBAccess.DBQ_SELECT + DBTable.tableName + DBAccess.DBS_WHERE.replace("id","AirportCode");

        try (final Connection con = getConnection(DBTable.host, DBTable.username, DBTable.password);
             PreparedStatement SELECT_BY_CODE = con.prepareStatement(DBTable.query)) {

            SELECT_BY_CODE.setString(1, id);
            final ResultSet resultSet = SELECT_BY_CODE.executeQuery();
            resultSet.next();
            airportByCode = generateRecordFromResultSet(resultSet);
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return airportByCode;
    }

    @Override
    public Airport generateRecordFromResultSet(ResultSet resultSet) {
        Airport airportByCode;

        try {
            final AirportCode airportCode = valueOf(resultSet.getString("AirportCode"));
            final Coordinate coordinate = jsonToProperty(resultSet.getString("Coordinate"), new Coordinate(0,0));
            final Location location = jsonToProperty(resultSet.getString("Location"), new Location("",""));
            final String name = resultSet.getString("AirportName");

            airportByCode = new Airport(airportCode, coordinate, location, name);
        } catch (IllegalArgumentException | SQLException e) {
            throw new RuntimeException(e);
        }

        return airportByCode;
    }

    public Location getAirportLocationByCode(AirportCode airportCode) {
        return getRecordById(airportCode.toString()).getLocation();
    }

    public Coordinate getAirportCoordinateByCode(AirportCode airportCode) {
        return getRecordById(airportCode.toString()).getCoordinate();
    }

    public String getAirportNameByCode(AirportCode airportCode) {
        return getRecordById(airportCode.toString()).getName();
    }
}