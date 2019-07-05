package com.taboola.sdksample;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabFragment extends Fragment {
    private static final String TAG = TabFragment.class.getSimpleName();
    private static final String EXTRA_KEY_PLACEMENT_NAME = "PLACEMENT_NAME";

    public static TabFragment newInstance(@NonNull String placementName) {
        Bundle data = new Bundle();
        data.putSerializable(EXTRA_KEY_PLACEMENT_NAME, placementName);

        TabFragment fragment = new TabFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Nullable
    private String getPlacementName() {
        Bundle data = getArguments();
        if (data != null) {
            return data.getString(EXTRA_KEY_PLACEMENT_NAME);
        } else {
            Log.e(TAG, "getPlacementName: placement name is not set");
            return null;
        }
    }
}
