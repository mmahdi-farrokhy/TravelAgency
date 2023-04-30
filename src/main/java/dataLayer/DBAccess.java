package dataLayer;

import commonStructures.DBTable;
import commonStructures.TableId;

import java.util.List;

public interface DBAccess<DBEntity extends DBTable> {
    String dbq_select = "SELECT * FROM ";
    String dbs_where = " WHERE id = ?";

    List<DBEntity> getAllRecords();
    DBEntity getRecordById(String id);
}
