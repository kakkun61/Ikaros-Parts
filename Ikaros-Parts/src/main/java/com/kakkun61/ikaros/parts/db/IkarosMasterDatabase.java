package com.kakkun61.ikaros.parts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IkarosMasterDatabase {
    private static final String DB_NAME = "master";
    private static final String ASSET_DB_PATH = "CraftFleet.db";
    private static final int DB_VERSION = 1;

    private IkarosMasterDatabase() {}

    public static SQLiteDatabase getReadableSQLiteDatabase(final Context context) throws IOException {
        try {
            final SQLiteDatabase database = SQLiteDatabase.openDatabase(context.getDatabasePath(DB_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);

            // DB が存在して最新なら
            if (database.getVersion() == DB_VERSION) {
                return database;
            }
        } catch (SQLiteException e) {
            // DB が存在しないとき
        }

        // DB が存在しない または 最新でない とき
        // aasset からコピー
        copyDataBaseFromAsset(context);

        addIdColumn(SQLiteDatabase.openDatabase(context.getDatabasePath(DB_NAME).getPath(), null, SQLiteDatabase.OPEN_READWRITE));

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
        createDatabaseFileIfNecessary(context);
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

    /**
     * {@link android.database.sqlite.SQLiteOpenHelper#getReadableDatabase()} を呼ぶことで {@link #DB_NAME} の DB ファイルを作る。
     * @param context
     */
    private static void createDatabaseFileIfNecessary(final Context context) {
        new SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {}

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
        }.getReadableDatabase();
    }

    /**
     * {@link android.widget.CursorAdapter} が {@code _id} カラムを必要とするので追加
     * @param db
     */
    private static void addIdColumn(final SQLiteDatabase db) {
        db.execSQL("ALTER TABLE Items ADD COLUMN '_id' INT");
    }
}
