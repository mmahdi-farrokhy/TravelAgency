package data.layer.crud;

import model.DBTable;

import java.sql.ResultSet;
import java.util.List;

public interface DBAccess<DBEntity extends DBTable> {
    String DBQ_SELECT = "SELECT * FROM ";
    String DBS_WHERE = " WHERE id = ?";

    List<DBEntity> getAllRecords();
    DBEntity getRecordById(String id);
    DBEntity generateRecordFromResultSet(ResultSet resultSet);
}
