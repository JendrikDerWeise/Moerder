package com.example.jendrik.moerder.GUI;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jendrik.moerder.R;


public class MenueDrawer extends AppCompatActivity {

    private Toolbar toolbar;

    private DrawerLayout drawerLayoutgesamt;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;

    private MapOverview map;
    private STUB_FRAG stub;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_drawer);

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        drawerLayoutgesamt = (DrawerLayout) findViewById(R.id.drawerlayoutgesamt);
        drawerToggle = new ActionBarDrawerToggle(MenueDrawer.this,drawerLayoutgesamt,R.string.auf, R.string.zu);
        drawerLayoutgesamt.setDrawerListener(drawerToggle);

        map = (MapOverview) Fragment.instantiate(this,MapOverview.class.getName(), null);
        stub = (STUB_FRAG) Fragment.instantiate(this,STUB_FRAG.class.getName(), null);

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_area, map);
        fragmentTransaction.commit();

        navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.map: {
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frag_area, map);
                        fragmentTransaction.commit();
                        break;
                    }

                    case R.id.list: {
                        //TODO
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frag_area, stub);
                        fragmentTransaction.commit();
                        break;
                    }

                    case R.id.suspect: {
                        //TODO
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frag_area, stub);
                        fragmentTransaction.commit();
                        break;
                    }

                    case R.id.weapon_change: {
                        //TODO
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frag_area, stub);
                        fragmentTransaction.commit();
                        break;
                    }

                    case R.id.indict: {
                        //TODO
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frag_area, stub);
                        fragmentTransaction.commit();
                        break;
                    }

                    case R.id.pause: {
                        //TODO
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frag_area, stub);
                        fragmentTransaction.commit();
                        break;
                    }

                    case R.id.help: {
                        //TODO
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frag_area, stub);
                        fragmentTransaction.commit();
                        break;
                    }
                }
                drawerLayoutgesamt.closeDrawers();
                menuItem.setChecked(true);
                return false;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.syncState();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        drawerToggle.syncState();
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        drawerToggle.onConfigurationChanged(new Configuration());
        super.onConfigurationChanged(newConfig);
    }
}