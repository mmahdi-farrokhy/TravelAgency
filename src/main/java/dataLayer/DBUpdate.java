package dataLayer;

import commonStructures.DBTable;

public interface DBUpdate<DBEntity extends DBTable> extends DBAccess{
    boolean insertNewRecord(DBEntity newRecord);
    boolean deleteRecordById(String id);
}