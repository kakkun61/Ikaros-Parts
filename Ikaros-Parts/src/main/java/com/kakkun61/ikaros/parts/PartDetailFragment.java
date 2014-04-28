package com.kakkun61.ikaros.parts;

import android.app.Fragment;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PartDetailFragment extends Fragment{
    private CharSequence name;
    private CharSequence rank;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle arguments = getArguments();
        name = arguments.getCharSequence("name");
        rank = arguments.getCharSequence("rank");
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(name);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.part_detail, container, false);
        ((TextView) view.findViewById(R.id.rank)).setText(rank);
        return view;
    }
}
