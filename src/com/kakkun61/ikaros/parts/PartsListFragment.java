package com.kakkun61.ikaros.parts;

import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import com.kakkun61.ikaros.parts.db.IkarosSQLiteOpenHelper;

public class PartsListFragment extends ListFragment {
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final SQLiteDatabase db = new IkarosSQLiteOpenHelper(getActivity()).getReadableDatabase();
        final Cursor items = db.query("Items", new String[]{"Rank", "ID"}, null, null, null, null, null, null);
        setListAdapter(new SimpleCursorAdapter(
                getActivity(),
                R.layout.pats_list_row,
                items,
                new String[]{"Rank", "ID"},
                new int[]{R.id.rank, R.id.name},
                0
        ));
    }
}

class RowData {

}