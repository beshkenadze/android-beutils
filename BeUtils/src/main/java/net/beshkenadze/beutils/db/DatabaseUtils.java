package net.beshkenadze.beutils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Aleksandr Beshkenadze <beshkenadze@gmail.com> on 30.09.13.
 */
public class DatabaseUtils {

    public static boolean checkDatabase(String dbPath) {

        SQLiteDatabase checkDB = null;

        try {
            checkDB = SQLiteDatabase.openDatabase(dbPath, null,
                    SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    public static void copyDatabase(Context context, String dbPath, String dbName) throws IOException {
        InputStream myInput = context.getAssets().open(dbName);

        String outFileName = dbPath + dbName;
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();

    }
}
