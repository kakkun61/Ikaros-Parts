package com.kakkun61.ikaros.parts;

import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleCursorAdapter;
import com.kakkun61.ikaros.parts.db.IkarosSQLiteOpenHelper;

import java.io.IOException;

public class PartsListFragment extends ListFragment {
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final IkarosSQLiteOpenHelper sqliteOpenHelper = new IkarosSQLiteOpenHelper(getActivity());
        try {
            sqliteOpenHelper.createEmptyDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final SQLiteDatabase db = sqliteOpenHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM sqlite_master WHERE type='table' ", null);

        Log.d("Ikaros Parts", String.valueOf(c.getCount()));

        boolean isEof = c.moveToFirst();

        while (isEof) {
            Log.d("Ikaros Parts", c.getString(1));
            isEof = c.moveToNext();
        }
    }
}

class RowData {

}