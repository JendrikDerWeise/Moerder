package com.example.jendrik.moerder.GUI.OnGamingClasses;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

import com.example.jendrik.moerder.GUI.STUB_FRAG;
import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.R;


public class MenueDrawer extends AppCompatActivity {
    private Game game;
    private Bundle extras;

    private Toolbar toolbar;

    private DrawerLayout drawerLayoutgesamt;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;

    private MapOverview map;
    private Suspect suspect;
    private STUB_FRAG stub;
    private ChangeWeapon changeWeapon;
    private Indict indict;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_drawer);
        extras = getIntent().getExtras();
        game = (Game) extras.get("GAME");
        //TODO verhindern das "Zurücktaste" von Android in die Spielerstellung zurück kehrt. Wie geht das? Ggf. finish()?
        //TODO Timer einbauen
        //TODO Runde beenden Methode --> erst game-Objekt abspeichern und senden --> finish(); --> startActivity(getIntent);
        //TODO Pro Runde eine Aktion --> Runde Beenden Aufruf in jedes Fragment, das als Aktion gilt
        //TODO Mitteilung "gerufen werden"
        //TODO Raum wechsel Fragment fehlt noch


        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        drawerLayoutgesamt = (DrawerLayout) findViewById(R.id.drawerlayoutgesamt);
        drawerToggle = new ActionBarDrawerToggle(MenueDrawer.this,drawerLayoutgesamt,R.string.auf, R.string.zu);
        drawerLayoutgesamt.setDrawerListener(drawerToggle);

        map = (MapOverview) Fragment.instantiate(this,MapOverview.class.getName(), null);
        suspect = (Suspect) Fragment.instantiate(this,Suspect.class.getName(), null);
        changeWeapon = (ChangeWeapon) Fragment.instantiate(this,ChangeWeapon.class.getName(),null);
        indict = (Indict) Fragment.instantiate(this,Indict.class.getName(),null);

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
                    //TODO mehrfachen Code in Methode auslagern
                    case R.id.map: {
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frag_area, map);
                        fragmentTransaction.commit();
                        break;
                    }

                    case R.id.list: {
                        //TODO muss noch erstellt werden
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frag_area, stub);
                        fragmentTransaction.commit();
                        break;
                    }

                    case R.id.suspect: {
                        //TODO Fehlermeldung "Du musst erst in einen Raum gehen/Waffe aufnehmen um einen Verdacht zu äußern"

                        if(game.getActivePlayer().getActualWeapon()==null || game.getActivePlayer().getActualRoom()==null){
                            fragmentManager = getFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frag_area, stub);
                            fragmentTransaction.commit();
                            break;
                        }else {
                            fragmentManager = getFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frag_area, suspect);
                            fragmentTransaction.commit();
                            break;
                        }
                    }

                    case R.id.weapon_change: {
                        //TODO Irgendwann austauschen gegen QR Scanner
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frag_area, changeWeapon);
                        fragmentTransaction.commit();
                        break;
                    }

                    case R.id.indict: {
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frag_area, indict);
                        fragmentTransaction.commit();
                        break;
                    }

                    case R.id.pause: {
                        //TODO Timer starten/stoppen, Broadcast
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frag_area, stub);
                        fragmentTransaction.commit();
                        break;
                    }

                    case R.id.help: {
                        //TODO muss noch überlegt werden, was wir reinschreiben wollen... und dann schreiben...
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

        //TODO checkTurn Methode um Titelleiste und Menü anzupassen wenn Spieler dran/nicht dran ist
        //checkTurn();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.syncState();

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

        }
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