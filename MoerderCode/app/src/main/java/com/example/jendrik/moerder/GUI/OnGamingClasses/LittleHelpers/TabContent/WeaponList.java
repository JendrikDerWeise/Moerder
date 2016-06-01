package com.example.jendrik.moerder.GUI.OnGamingClasses.LittleHelpers.TabContent;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jendrik.moerder.R;

/**
 * Created by Jendrik on 25.03.2016.
 */
public class WeaponList extends Fragment {
    View contentView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rvadapter;
    private RecyclerView.LayoutManager rvLayoutManager;

    public static TextView tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView = inflater.inflate(R.layout.tab_content, null);
        final Activity fA = getActivity();
        recyclerView = (RecyclerView) contentView.findViewById(R.id.suspect_list_recyclerview);
        rvLayoutManager = new LinearLayoutManager(fA);
        recyclerView.setLayoutManager(rvLayoutManager);

        rvadapter = new SuspectListAdapterClassWeapon();
        recyclerView.setAdapter(rvadapter);


        tv = (TextView) fA.findViewById(R.id.textViewTab);

        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}