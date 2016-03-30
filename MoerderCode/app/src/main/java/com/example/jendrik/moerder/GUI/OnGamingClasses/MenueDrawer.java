package com.example.jendrik.moerder.GUI.OnGamingClasses;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.jendrik.moerder.GUI.Host.STUB_FRAG;
import com.example.jendrik.moerder.GUI.OnGamingClasses.LittleHelpers.CountDownClass;
import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.R;


public class MenueDrawer extends AppCompatActivity {
    public static Game game;
    private Bundle extras;

    private Toolbar toolbar;

    private DrawerLayout drawerLayoutgesamt;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;

    private MapOverview map;
    private NoticeList noticeList;
    private ShowClues clues;
    private Suspect suspect;
    private STUB_FRAG stub;
    private ChangeWeapon changeWeapon;
    private ChangeWeaponError changeWeaponError;
    private ChangeRoom changeRoom;
    private Indict indict;
    private IndictError indictError;
    private Pause pause;
    public static Context cont;
    private static FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private CountDownClass timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_drawer);
        extras = getIntent().getExtras();
        game = (Game) extras.get("GAME");
        //TODO verhindern das "Zurücktaste" von Android in die Spielerstellung zurück kehrt. Wie geht das? Ggf. finish()?
        //TODO Mitteilung "gerufen werden"
        //TODO Raum wechsel Fragment fehlt noch


        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        drawerLayoutgesamt = (DrawerLayout) findViewById(R.id.drawerlayoutgesamt);
        drawerToggle = new ActionBarDrawerToggle(MenueDrawer.this,drawerLayoutgesamt,R.string.auf, R.string.zu);
        drawerLayoutgesamt.setDrawerListener(drawerToggle);

        map = (MapOverview) Fragment.instantiate(this, MapOverview.class.getName(), null);
        noticeList = (NoticeList) Fragment.instantiate(this,NoticeList.class.getName(), null);
        clues = (ShowClues) Fragment.instantiate(this,ShowClues.class.getName(),null);
        suspect = (Suspect) Fragment.instantiate(this,Suspect.class.getName(), null);
        changeWeapon = (ChangeWeapon) Fragment.instantiate(this,ChangeWeapon.class.getName(),null);
        changeWeaponError = (ChangeWeaponError) Fragment.instantiate(this,ChangeWeaponError.class.getName(),null);
        changeRoom = (ChangeRoom) Fragment.instantiate(this,ChangeRoom.class.getName(),null);
        indict = (Indict) Fragment.instantiate(this,Indict.class.getName(),null);
        indictError = (IndictError) Fragment.instantiate(this,IndictError.class.getName(),null);
        pause = (Pause) Fragment.instantiate(this,Pause.class.getName(),null);

        stub = (STUB_FRAG) Fragment.instantiate(this,STUB_FRAG.class.getName(), null);

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_area, map);
        fragmentTransaction.commit();

        timer = new CountDownClass(this, game.getMin(),game.getSec());
        timer.getTimer().start();

        navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.map: {
                        menueSetter(map);
                        break;
                    }

                    case R.id.list: {
                        menueSetter(noticeList);
                        break;
                    }

                    case R.id.clues:{
                        menueSetter(clues);
                        break;
                    }

                    case R.id.suspect: {
                        if(game.getActivePlayer().getActualWeapon()==null || game.getActivePlayer().getActualRoom().getName().equals(game.getGrpRoom().getName())){
                            menueSetter(indictError);
                            break;
                        }else {
                            menueSetter(suspect);
                            break;
                        }
                    }

                    case R.id.weapon_change: {
                        if(game.getActivePlayer().getActualRoom().getWeaponList().isEmpty())
                            menueSetter(changeWeaponError);
                        else
                            menueSetter(changeWeapon);
                        break;
                    }

                    case R.id.room_change: {
                        menueSetter(changeRoom);
                        break;
                    }

                    case R.id.indict: {
                        menueSetter(indict);
                        break;
                    }

                    case R.id.pause: {
                        //TODO Pause Broadcast
                        timer.pause();
                        menueSetter(pause);
                        drawerToggle.setDrawerIndicatorEnabled(false);
                        break;
                    }

                    case R.id.help: {
                        //TODO muss noch überlegt werden, was wir reinschreiben wollen... und dann schreiben...
                        menueSetter(stub);
                        break;
                    }
                }
                drawerLayoutgesamt.closeDrawers();
                menuItem.setChecked(true);
                cont = getApplicationContext();
                return false;
            }
        });

        //TODO checkTurn Methode um Titelleiste und Menü anzupassen wenn Spieler dran/nicht dran ist
        //checkTurn();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.syncState();


    }

    private void menueSetter(Fragment frag){
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_area, frag);
        fragmentTransaction.commit();
    }

    public void endTurn(){
        getIntent().putExtra("GAME",game);
        finish();
        startActivity(getIntent());
    }

    public void onClickInDrawer(View v){
        switch(v.getId()){
            case R.id.btn_suspect:
                suspect.onClickSuspect();
                break;
            case R.id.btn_indict:
                indict.onClickIndict(v);
                break;
            case R.id.btn_yes:
                indict.onClickYes();
                break;
            case R.id.btn_no:
                indict.onClickNo();
                break;
            case R.id.btn_ok_suspect:
                endTurn();
                break;
            case R.id.btn_resume:
                drawerToggle.setDrawerIndicatorEnabled(true);
                menueSetter(map);
                timer.resume();
                break;
        }
    }
/*
        Auto-generated methods
 */

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