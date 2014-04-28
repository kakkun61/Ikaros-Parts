package com.kakkun61.ikaros.parts;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.kakkun61.ikaros.parts.db.IkarosMasterDatabase;
import com.kakkun61.ikaros.parts.model.DatabaseInfo.Parts;

import java.io.IOException;

public class PartsListFragment extends ListFragment {
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            final SQLiteDatabase db = IkarosMasterDatabase.getReadableSQLiteDatabase(getActivity());
            final Cursor items = db.query(Parts.name, null, null, null, null, null, null, null);
            setListAdapter(new SimpleCursorAdapter(
                    getActivity(),
                    R.layout.parts_list_row,
                    items,
                    new String[]{"Rank", "ItemName"},
                    new int[]{R.id.rank, R.id.name},
                    0
            ));
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Failed to copy DB", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ViewHolder data = (ViewHolder) v.getTag();
        if (data == null) {
            data = new ViewHolder();
            data.rank = (TextView) v.findViewById(R.id.rank);
            data.name = (TextView) v.findViewById(R.id.name);
            v.setTag(data);
        }
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final PartDetailFragment partDetailFragment = new PartDetailFragment();
        fragmentTransaction.hide(this);
        Bundle args = new Bundle();
        args.putCharSequence("name", data.name.getText());
        args.putCharSequence("rank", data.rank.getText());
        partDetailFragment.setArguments(args);
        fragmentTransaction.add(R.id.contents, partDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private class ViewHolder {
        public TextView rank;
        public TextView name;
    }
}
