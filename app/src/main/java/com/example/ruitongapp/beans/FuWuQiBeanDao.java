package com.example.ruitongapp.beans;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "FU_WU_QI_BEAN".
*/

public class FuWuQiBeanDao extends AbstractDao<FuWuQiBean, Long> {

    public static final String TABLENAME = "FU_WU_QI_BEAN";

    /**
     * Properties of entity FuWuQiBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Dizhi = new Property(1, String.class, "dizhi", false, "DIZHI");
        public final static Property IsTrue = new Property(2, boolean.class, "isTrue", false, "IS_TRUE");
    }


    public FuWuQiBeanDao(DaoConfig config) {
        super(config);
    }
    
    public FuWuQiBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FU_WU_QI_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: id
                "\"DIZHI\" TEXT," + // 1: dizhi
                "\"IS_TRUE\" INTEGER NOT NULL );"); // 2: isTrue
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FU_WU_QI_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, FuWuQiBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String dizhi = entity.getDizhi();
        if (dizhi != null) {
            stmt.bindString(2, dizhi);
        }
        stmt.bindLong(3, entity.getIsTrue() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, FuWuQiBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String dizhi = entity.getDizhi();
        if (dizhi != null) {
            stmt.bindString(2, dizhi);
        }
        stmt.bindLong(3, entity.getIsTrue() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public FuWuQiBean readEntity(Cursor cursor, int offset) {
        FuWuQiBean entity = new FuWuQiBean( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // dizhi
            cursor.getShort(offset + 2) != 0 // isTrue
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, FuWuQiBean entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setDizhi(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setIsTrue(cursor.getShort(offset + 2) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(FuWuQiBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(FuWuQiBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(FuWuQiBean entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
