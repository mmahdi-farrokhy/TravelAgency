package dataLayer;

import commonStructures.DBTable;

public interface DBUpdate<DBEntity extends DBTable> extends DBAccess<DBEntity>{
    public static String dbq_insert = " INSERT INTO ";
    public static String dbq_delete = " DELETE FROM ";

    boolean insertNewRecord(DBEntity newRecord);
    void deleteRecordById(String id);
}