package databaseConnection;

import commonStructures.TableId;
import flight.Airport;
import flight.Coordinate;
import flight.Location;

import java.util.List;

public interface DBAccess {
    List<Airport> getAllRecords();
    Airport getRecordById(TableId id);
    Location getRecordLocationById(TableId id);
    Coordinate getRecordCoordinateById(TableId id);
}
