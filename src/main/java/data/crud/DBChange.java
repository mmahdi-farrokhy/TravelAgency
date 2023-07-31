package data.crud;

import model.DBTable;

public interface DBChange<DBEntity extends DBTable> extends DBAccess<DBEntity>{
    String DBQ_INSERT = "INSERT INTO ";
    String DBQ_DELETE = "DELETE FROM ";

    boolean insertNewRecord(DBEntity newRecord);
    void deleteRecordById(String id);
}