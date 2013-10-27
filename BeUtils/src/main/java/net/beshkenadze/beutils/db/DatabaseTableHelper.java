package net.beshkenadze.beutils.db;


import android.database.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;

import java.sql.Savepoint;
import java.util.List;

/**
 * Created by akira on 30.09.13.
 */
public class DatabaseTableHelper<T> {
    private Dao mDao = null;
    private DatabaseHelper mHelper;
    private Class<?> mClass;

    public Dao getDao() {
        try {
            return mHelper.getDao(mClass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DatabaseTableHelper(DatabaseHelper h, Class<?> c) {
        mHelper = h;
        mClass = c;
        mDao = getDao();
    }


    public DatabaseHelper getHelper() {
        return mHelper;
    }

    public boolean add(Object mObject) {
        try {
            getDao().create(mObject);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Object mObject) {
        try {
            getDao().update(mObject);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Object findById(int id) {
        try {
            return getDao().queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long count() {
        try {
            return getDao().countOf();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<?> getAll() {
        try {
            return getDao().queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void flush() {
        try {
            TableUtils.clearTable(mHelper.getConnectionSource(), mClass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(Object mObject) {
        if (mObject != null) {
            try {
                getDao().delete(mObject);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (java.sql.SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveBulkData(List<Object> objectTypes) {
        long t1 = System.nanoTime();
        try {
            DatabaseConnection conn = getDao().startThreadConnection();
            Savepoint savepoint = null;
            try {
                savepoint = conn.setSavePoint(null);
                doInsert(objectTypes, getDao());
            } finally {
                conn.commit(savepoint);
                getDao().endThreadConnection(conn);
            }
            long t2 = System.nanoTime();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    private void doInsert(List<Object> objectTypes, Dao<Object, Integer> dao) {
        for (Object a : objectTypes) {
            try {
                if (a != null)
                    dao.createOrUpdate(a);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
