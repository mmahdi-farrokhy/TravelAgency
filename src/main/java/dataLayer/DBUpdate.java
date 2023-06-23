package dataLayer;

import model.DBTable;

public interface DBUpdate<DBEntity extends DBTable> extends DBChange<DBEntity> {
    String DBQ_UPDATE = "UPDATE table SET columnValuePairs";

    boolean updateRecord(DBEntity newRecord);
}
