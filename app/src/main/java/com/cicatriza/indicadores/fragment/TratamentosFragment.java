package com.cicatriza.indicadores.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cicatriza.indicadores.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TratamentosFragment extends Fragment {


    public TratamentosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tratamentos, container, false);
    }

}
