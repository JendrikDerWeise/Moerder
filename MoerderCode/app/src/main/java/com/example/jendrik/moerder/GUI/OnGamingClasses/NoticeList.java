package com.example.jendrik.moerder.GUI.OnGamingClasses;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jendrik.moerder.GUI.LittleHelpers.TabContent.ViewPagerAdapter;
import com.example.jendrik.moerder.R;


/**
 * Created by Jendrik on 25.03.2016.
 */
public class NoticeList extends Fragment {

    private View fragLayoutV;
    private FragmentTabHost tabHost;

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragLayoutV = inflater.inflate(R.layout.fragment_list, container, false);
        createTabs();

        return fragLayoutV;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * Methode baut ein Tab-Dingsi
     * Die "onTabSelected" Methoden sprechen fuer sich.
     */
    private void createTabs(){

        toolbar = (Toolbar) fragLayoutV.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        viewPager = (ViewPager) fragLayoutV.findViewById(R.id.viewpager);
        Context context = getActivity().getApplicationContext();
        final ViewPagerAdapter viewPagerAdpter = new ViewPagerAdapter(((AppCompatActivity)getActivity()).getSupportFragmentManager(), context);
        viewPager.setAdapter(viewPagerAdpter);


        tabLayout = (TabLayout) fragLayoutV.findViewById(R.id.tablayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });




    }


}
