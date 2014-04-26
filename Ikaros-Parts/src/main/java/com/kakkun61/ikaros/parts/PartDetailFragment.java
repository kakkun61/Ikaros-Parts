package com.kakkun61.ikaros.parts;

import android.app.Fragment;
import android.os.Bundle;

public class PartDetailFragment extends Fragment{
    private CharSequence name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle arguments = getArguments();
        name = arguments.getCharSequence("name");
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(name);
    }
}
