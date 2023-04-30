package dataLayer;

import commonStructures.DBTable;

public interface DBUpdate<DBEntity extends DBTable> extends DBAccess<DBEntity>{
    String DBQ_INSERT = " INSERT INTO ";
    String DBQ_DELETE = " DELETE FROM ";

    boolean insertNewRecord(DBEntity newRecord);
    void deleteRecordById(String id);
}