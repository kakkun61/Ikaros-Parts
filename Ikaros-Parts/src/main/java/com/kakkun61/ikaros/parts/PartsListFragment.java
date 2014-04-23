package com.kakkun61.ikaros.parts;

import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import com.kakkun61.ikaros.parts.db.IkarosMasterDatabase;

import java.io.IOException;

public class PartsListFragment extends ListFragment {
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            final SQLiteDatabase db = IkarosMasterDatabase.getReadableSQLiteDatabase(getActivity());
            final Cursor items = db.query("Items", null, null, null, null, null, null, null);
            setListAdapter(new SimpleCursorAdapter(
                    getActivity(),
                    R.layout.pats_list_row,
                    items,
                    new String[]{"Rank", "ID"},
                    new int[]{R.id.rank, R.id.name},
                    0
            ));
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Failed to copy DB", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}

class RowData {

}
