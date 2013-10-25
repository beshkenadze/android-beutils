package net.beshkenadze.beutils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandr Beshkenadze <beshkenadze@gmail.com> on 30.09.13.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static DatabaseHelper sInstance = null;

    private List<Class<?>> mTables = new ArrayList<Class<?>>();

    private Context context;

    public DatabaseHelper(Context context, String databaseName, int databaseVersion,
                          List<Class<?>> tables) {
        super(context, databaseName, null, databaseVersion);
        this.mTables = tables;

    }


    public static DatabaseHelper getInstance(Context context, String databaseName, int databaseVersion,
                                             List<Class<?>> tables) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext(), databaseName,
                    databaseVersion, tables);
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase,
                         ConnectionSource connectionSource) {
        // Создаем таблицы, если они не существуют
        for (Class<?> dbClass : mTables) {
            try {
                TableUtils.createTableIfNotExists(connectionSource, dbClass);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase,
                          ConnectionSource connectionSource, int oldVer, int newVer) {

        // Обновление, удаляем таблицы,
        for (Class<?> dbclass : mTables) {
            try {
                TableUtils.dropTable(connectionSource, dbclass, true);
            } catch (Exception e) {
            }
        }
        onCreate(sqliteDatabase, connectionSource);
    }

    @Override
    public synchronized void close() {
        super.close();
    }

}
