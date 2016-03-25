package com.example.jendrik.moerder.GUI.OnGamingClasses.LittleHelpers.TabContent;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jendrik.moerder.R;

public class ViewPagerAdapter extends FragmentPagerAdapter{

    String[] tabtitlearray = new String[3];

    public ViewPagerAdapter (FragmentManager manager, Context context){

        super(manager);
        String tab1 = context.getResources().getString(R.string.tab_person);
        String tab2 = context.getResources().getString(R.string.tab_room);
        String tab3 = context.getResources().getString(R.string.tab_weapon);
        tabtitlearray[0] = tab1;
        tabtitlearray[1] = tab2;
        tabtitlearray[2] = tab3;
    }



    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0: return new PersonList();
            case 1: return new RoomList();
            case 2: return new WeaponList();
        }


        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return tabtitlearray[position];
    }
} 

