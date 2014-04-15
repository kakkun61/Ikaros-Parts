package com.kakkun61.ikaros.parts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.*;

public class IkarosMasterDatabase {
    private static final String DB_NAME = "master";
    private static final String ASSET_DB_PATH = "CraftFleet.db";
    private static final int DB_VERSION = 1;

    private IkarosMasterDatabase() {}

    public static SQLiteDatabase getReadableSQLiteDatabase(final Context context) throws IOException {
        final SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(context.getDatabasePath(DB_NAME).getPath(), null);

        // DB が存在して最新なら
        if (database.getVersion() == DB_VERSION) {
            return database;
        }

        // DB を新しく作った、または最新でないとき
        // asset からコピー
        copyDataBaseFromAsset(context);

        return SQLiteDatabase.openDatabase(context.getDatabasePath(DB_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);
    }

    /**
     * asset の db ファイルから実際の DB にコピーする
     */
    private static void copyDataBaseFromAsset(final Context context) throws IOException {
        Log.d("call", "copyDataBaseFromAsset");

        // asset 内のデータベースファイルにアクセス
        InputStream input = context.getAssets().open(ASSET_DB_PATH);

        // デフォルトのデータベースパスに作成した空のDB
        OutputStream output = new FileOutputStream(context.getDatabasePath(DB_NAME));

        // コピー
        byte[] buffer = new byte[1024];
        int size;
        long totalSize = 0;
        while ((size = input.read(buffer)) > 0) {
            output.write(buffer, 0, size);
            totalSize += size;
        }

        // Close the streams
        output.flush();
        output.close();
        input.close();
        Log.d("asset db size", "" + totalSize);
        Log.d("data db size (just after copy)", "" + new File(context.getDatabasePath(DB_NAME).getPath()).length());
    }
}
