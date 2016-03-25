package com.example.jendrik.moerder.GUI.OnGamingClasses.LittleHelpers.TabContent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.jendrik.moerder.R;

/**
 * Created by Jendrik on 25.03.2016.
 */
public class RoomList extends Fragment {
    View contentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView = inflater.inflate(R.layout.tab_content, null);


        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Button btn = (Button) contentView.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Button", Toast.LENGTH_SHORT).show();
            }
        });


        super.onViewCreated(view, savedInstanceState);
    }
}
