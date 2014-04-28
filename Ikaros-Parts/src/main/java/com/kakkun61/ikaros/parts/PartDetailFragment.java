package com.kakkun61.ikaros.parts;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kakkun61.ikaros.parts.model.PartModel;

public class PartDetailFragment extends Fragment{
    private PartModel part;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle arguments = getArguments();
        part = (PartModel) arguments.getSerializable("part");
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(part.name);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.part_detail, container, false);
        ((TextView) view.findViewById(R.id.rank)).setText(String.valueOf(part.rank));
        return view;
    }
}
