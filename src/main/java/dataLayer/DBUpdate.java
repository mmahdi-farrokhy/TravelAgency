package dataLayer;

import commonStructures.DBTable;

public interface DBUpdate<DBEntity extends DBTable> extends DBAccess<DBEntity>{
    boolean insertNewRecord(DBEntity newRecord);
    void deleteRecordById(String id);
}