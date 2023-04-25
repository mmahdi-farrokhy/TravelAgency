package dataLayer;

import commonStructures.TableId;

import java.util.List;

public interface DBAccess<TEntity extends DBTable> {
    List<TEntity> getAllRecords();
    TEntity getRecordById(TableId id);
}
