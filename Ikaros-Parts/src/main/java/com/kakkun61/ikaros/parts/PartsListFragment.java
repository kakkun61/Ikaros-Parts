package com.kakkun61.ikaros.parts;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kakkun61.android.SimpleArrayAdapter;
import com.kakkun61.ikaros.parts.db.IkarosMasterDatabase;
import com.kakkun61.ikaros.parts.model.DatabaseInfo.Parts;
import com.kakkun61.ikaros.parts.model.PartModel;

import java.io.IOException;

public class PartsListFragment extends ListFragment {
    private ArrayAdapter<PartModel> partsAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            final SQLiteDatabase db = IkarosMasterDatabase.getReadableSQLiteDatabase(getActivity());
            final Cursor items = db.query(Parts.name, null, null, null, null, null, null, null);
            final PartModel[] parts = PartModel.convert(items);
            partsAdapter = new SimpleArrayAdapter<PartModel>(
                    getActivity(),
                    R.layout.parts_list_row,
                    parts,
                    new SimpleArrayAdapter.Mapper<PartModel>(){
                        @Override
                        public void map(PartModel item, View view) {
                            ViewHolder holder = ViewHolder.viewGetTag(view);
                            holder.name.setText(item.name);
                            holder.rank.setText(String.valueOf(item.rank));
                        }
                    }
            );
            setListAdapter(partsAdapter);
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Failed to copy DB", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        final PartModel part = partsAdapter.getItem(position);
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final PartDetailFragment partDetailFragment = new PartDetailFragment();
        fragmentTransaction.hide(this);
        Bundle args = new Bundle();
        args.putSerializable("part", part);
        partDetailFragment.setArguments(args);
        fragmentTransaction.add(R.id.contents, partDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private static class ViewHolder {
        public TextView rank;
        public TextView name;

        public static ViewHolder viewGetTag(final View view) {
            ViewHolder holder = (ViewHolder) view.getTag();
            if (holder != null) {
                return holder;
            }
            holder = new ViewHolder();
            holder.rank = (TextView) view.findViewById(R.id.rank);
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
            return holder;
        }
    }
}
