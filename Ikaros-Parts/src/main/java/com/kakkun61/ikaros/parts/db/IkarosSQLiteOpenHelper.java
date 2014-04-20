package com.kakkun61.ikaros.parts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IkarosSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "com.kakkun61.ikaros.parts";
    private static final String ASSET_DB_PATH = "CraftFleet.db";
    private static final int DB_VERSION = 1;

    private final Context context;

    public IkarosSQLiteOpenHelper(final Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            copyDataBaseFromAsset();
        } catch (IOException e) {
            Log.d("tenkura parts", e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        context.getDatabasePath(DB_NAME).delete();
        try {
            copyDataBaseFromAsset();
        } catch (IOException e) {
            Log.d("tenkura parts", e.toString());
        }
    }

    /**
     * asset の db ファイルから実際の DB にコピーする
     */
    private void copyDataBaseFromAsset() throws IOException {

        // asset 内のデータベースファイルにアクセス
        InputStream input = context.getAssets().open(ASSET_DB_PATH);

        // デフォルトのデータベースパスに作成した空のDB
        OutputStream output = new FileOutputStream(context.getDatabasePath(DB_NAME));

        // コピー
        byte[] buffer = new byte[1024];
        int size;
        while ((size = input.read(buffer)) > 0) {
            output.write(buffer, 0, size);
        }

        // Close the streams
        output.flush();
        output.close();
        input.close();
    }
}
