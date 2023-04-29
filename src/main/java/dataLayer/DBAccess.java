package dataLayer;

import commonStructures.DBTable;
import commonStructures.TableId;

import java.util.List;

public interface DBAccess<DBEntity extends DBTable> {
    String dbs_where = " WHERE AirportCode = ?";

    List<DBEntity> getAllRecords();
    DBEntity getRecordById(String id);
}
