package dataLayer;

import commonStructures.DBTable;
import commonStructures.TableId;

import java.util.List;

public interface DBAccess<DBEntity extends DBTable> {
    List<DBEntity> getAllRecords();
    DBEntity getRecordById(String id);
}
