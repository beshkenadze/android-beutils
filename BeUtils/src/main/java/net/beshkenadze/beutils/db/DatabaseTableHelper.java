package net.beshkenadze.beutils.db;


import android.database.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.util.List;

/**
 * Created by akira on 30.09.13.
 */
public class DatabaseTableHelper {
    private Dao mDao = null;
    private DatabaseHelper mHelper;
    private Class<?> mClass;

    public Dao<Object, Integer> getDao() {
        return mDao;
    }

    public DatabaseTableHelper(DatabaseHelper h, Class<?> c) {

        mHelper = h;
        mClass = c;

        try {
            mDao = mHelper.getDao(mClass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
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
}
