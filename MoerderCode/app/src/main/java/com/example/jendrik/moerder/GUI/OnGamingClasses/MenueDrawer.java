package com.example.jendrik.moerder.GUI.OnGamingClasses;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.jendrik.moerder.FCM.FCMRunningGameListener;
import com.example.jendrik.moerder.FCM.SendToDatabase;
import com.example.jendrik.moerder.GUI.Host.STUB_FRAG;
import com.example.jendrik.moerder.GUI.LittleHelpers.CountDownClass;
import com.example.jendrik.moerder.GUI.LittleHelpers.NotificationBuilder;
import com.example.jendrik.moerder.GUI.LittleHelpers.PopUpBack;
import com.example.jendrik.moerder.GUI.LittleHelpers.SuspectionHelpers.PopUpShowSuspectorTheResult;
import com.example.jendrik.moerder.GUI.LittleHelpers.SuspectionHelpers.PopUpSuspectionInformPlayerWhoHasClue;
import com.example.jendrik.moerder.GUI.LittleHelpers.ProsecutionHelpers.PopUpIndict;
import com.example.jendrik.moerder.GUI.LittleHelpers.ProsecutionHelpers.PopUpIndictPlayerBroadcast;
import com.example.jendrik.moerder.GUI.LittleHelpers.ProsecutionHelpers.ProsecutionFromOtherPlayer;
import com.example.jendrik.moerder.GUI.LittleHelpers.ProsecutionHelpers.ProsecutionWaitingForPlayers;
import com.example.jendrik.moerder.GUI.LittleHelpers.SuspectionHelpers.PopupSuspectionShowPlayersTheResult;
import com.example.jendrik.moerder.GUI.LittleHelpers.SuspectionHelpers.PupUpSuspectionCallSinglePlayer;
import com.example.jendrik.moerder.GUI.LittleHelpers.SuspectionHelpers.Suspection;
import com.example.jendrik.moerder.GUI.Startscreen;
import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.GameObjekts.Player;
import com.example.jendrik.moerder.GameObjekts.Room;
import com.example.jendrik.moerder.GameObjekts.Solution;
import com.example.jendrik.moerder.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MenueDrawer extends AppCompatActivity implements GameIsRunningCallback {
    public static Game game;

    private DrawerLayout drawerLayoutgesamt;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;

    private MapOverview map;
    private NoticeList noticeList;
    private ShowClues clues;
    private Suspect suspect;
    private SuspectError suspectError;
    private STUB_FRAG stub;
    private ChangeWeapon changeWeapon;
    private ChangeWeaponError changeWeaponError;
    private ChangeRoom changeRoom;
    private Indict indict;
    private IndictError indictError;
    private ProsecutionFromOtherPlayer prosecutionFromOtherPlayer;
    private Pause pause;
    public static Context cont;
    private CountDownClass timer;
    public static int whoAmI;
    public static boolean myTurn;
    private FCMRunningGameListener fcm;
    private String pNumber;
    private SendToDatabase stb;
    private NotificationBuilder mBuilder;
    NotificationManager mNotificationManager;


    /**
     * Methode erstellt das linke Menü und initialisiert den Timer
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        whoAmI = getIntent().getExtras().getInt("whoAmI");
        game =(Game) getIntent().getExtras().get("GAME");
        myTurn = getIntent().getBooleanExtra("myTurn", false);

        if(!myTurn)
            this.setTheme(R.style.mordThemeNotTurnWithDrawer);
        else
            this.setTheme(R.style.mordThemeWithDrawer);

        setContentView(R.layout.menu_drawer);

        if(!MenueDrawer.myTurn)
            findViewById(R.id.toolbar1).setBackgroundResource(R.color.colorOtherTurn);
        else
            findViewById(R.id.toolbar1).setBackgroundResource(R.color.colorPrimary);

        setLayout();
        instantiateFragments();
        initFragManager();
        initNaviListener();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.syncState();

        pNumber = "" + whoAmI;
        stb = new SendToDatabase(game.getGameName(),pNumber);
        fcm = new FCMRunningGameListener(game.getGameName(), this);
        fcm.roomListListener();
        fcm.playerListListener();
        fcm.pauseListener();
        fcm.activePlayerListener();
        fcm.prosecutionNotifyListener();
        fcm.suspectionNotifyListener();

        mBuilder = new NotificationBuilder(this, getIntent().getExtras());
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void onStart(){
        super.onStart();

        if(!myTurn){
            notActive();
        }else {
            String str = getResources().getString(R.string.your_turn_txt);
            timer = new CountDownClass(this, (int) game.getMin(), (int) game.getSec(), str);
            timer.getTimer().start();
        }
    }

    @Override
    public void onBackPressed(){
        DialogFragment backWarning = new PopUpBack();
        backWarning.show(getFragmentManager(), "PopUpBack");
    }

    /**
     * Festlegen des Layouts
     * Menu(Drawer) wird zugewiesen
     */
    private void setLayout(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        drawerLayoutgesamt = (DrawerLayout) findViewById(R.id.drawerlayoutgesamt);
        drawerToggle = new ActionBarDrawerToggle(MenueDrawer.this,drawerLayoutgesamt,R.string.auf, R.string.zu);
        drawerLayoutgesamt.addDrawerListener(drawerToggle);
    }

    /**
     * Fragments sind im Grunde Activitys innerhalb von Activitys. Weil das aber nicht geht, nimmt man Fragments.
     * Ähnlich wie mit Intents, muss man ihnen die entsprechende Klasse zuweisen.
     * Durch die Zuweisung hier, wird später die korrekte Klasse bei Klick auf Menüpunkt gestartet.
     */
    private void instantiateFragments(){
        map = (MapOverview) Fragment.instantiate(this, MapOverview.class.getName(), null);
        noticeList = (NoticeList) Fragment.instantiate(this,NoticeList.class.getName(), null);
        clues = (ShowClues) Fragment.instantiate(this,ShowClues.class.getName(),null);
        suspect = (Suspect) Fragment.instantiate(this,Suspect.class.getName(), null);
        suspectError = (SuspectError) Fragment.instantiate(this,SuspectError.class.getName(),null);
        changeWeapon = (ChangeWeapon) Fragment.instantiate(this,ChangeWeapon.class.getName(),null);
        changeWeaponError = (ChangeWeaponError) Fragment.instantiate(this,ChangeWeaponError.class.getName(),null);
        changeRoom = (ChangeRoom) Fragment.instantiate(this,ChangeRoom.class.getName(),null);
        indict = (Indict) Fragment.instantiate(this,Indict.class.getName(),null);
        indictError = (IndictError) Fragment.instantiate(this,IndictError.class.getName(),null);
        prosecutionFromOtherPlayer = (ProsecutionFromOtherPlayer) Fragment.instantiate(this,ProsecutionFromOtherPlayer.class.getName(),null);
        pause = (Pause) Fragment.instantiate(this,Pause.class.getName(),null);

        stub = (STUB_FRAG) Fragment.instantiate(this,STUB_FRAG.class.getName(), null);
    }

    /**
     * Zur Verwaltung und Bedienung der Fragments wird ein Manager benötigt.
     * Es muss ein Startfragment festgelegt werden. Hier = map
     * Eigentlich ist die Methode Unsinn, aber so wird es deutlicher.
     */
    private void initFragManager(){
        drawerToggle.setDrawerIndicatorEnabled(true);
        menueSetter(map);
    }

    /**
     * Immer wenn ein anderer Menüpunkt geklickt wird, muss der Fragmentmanager umgestellt werden.
     * @param frag erwartet ein entsprechendes Fragment, also die jeweilige Klasse die angezeigt werden soll (siehe instantiateFragments)
     */
    private void menueSetter(Fragment frag){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_area, frag);
        fragmentTransaction.commit();
    }

    private void initNaviListener(){
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
                    case R.id.drawer_suspect: {
                        if(game.getActivePlayer().getActualWeapon()==null || game.getActivePlayer().getActualRoom().getName().equals(game.getGrpRoom().getName())){
                            menueSetter(suspectError);
                            break;
                        }else {
                            menueSetter(suspect);
                            break;
                        }
                    }
                    case R.id.drawer_weapon_change: {
                        if(game.getActivePlayer().getActualRoom().getWeaponList() == null)
                            menueSetter(changeWeaponError);
                        else
                            menueSetter(changeWeapon);
                        break;
                    }
                    case R.id.drawer_room_change: {
                        menueSetter(changeRoom);
                        break;
                    }
                    case R.id.drawer_indict: {
                        if(game.getPlayerManager().getPlayerList().get(whoAmI).getActualRoom().getName().equals(game.getGrpRoom().getName())) {
                            DialogFragment indictWarning = new PopUpIndict();
                            indictWarning.show(getFragmentManager(), "PopUpIndict");
                        }else
                            menueSetter(indictError);
                        break;
                    }
                    case R.id.pause: {
                        if(myTurn)
                            timer.pause();
                        menueSetter(pause);
                        stb.updateData("paused", true);
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
    }

    /**
     * das Spielobjekt wird in die Extras abgelegt.
     * Activity wird anschliessend neu gestartet.
     */
    public void endTurn(){
        game.setNextActivePlayer();
        myTurn = false;

        stb.updateData("playerManager", game.getPlayerManager());
        stb.updateData("aktivePlayer", game.getPlayerManager().getAktivePlayer());
        stb.updateData("roomList", game.getRoomManager().getRoomList());

        getIntent().putExtra("GAME",game);
        getIntent().putExtra("myTurn", false);
        finish();
        if(!game.getPlayerManager().getPlayerList().get(whoAmI).isDead())
            startActivity(getIntent());
        else
            fcm.unbindListeners();
    }


    /**
     * onClickEvents innerhalb von Fragments funktionieren nicht. Daher wird bei jedem Klick auf einem
     * Button innerhalb der Fragments diese Methode aufgerufen und leitet den Klick an die entsprechende
     * onClick innerhalb des zugehoerigen Fragments weiter.
     * @param v
     */
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
                stb.updateData("paused", false);
                break;
        }
    }

    private void notActive(){
        setInvisible(R.id.drawer_suspect);
        setInvisible(R.id.drawer_indict);
        setInvisible(R.id.drawer_weapon_change);
        setInvisible(R.id.drawer_room_change);
    }

    private void setInvisible(int id){
        navigationView.getMenu().findItem(id).setVisible(false);
    }


    /**
     * Callback Funktion für FCM Listener
     * Wird ausgelöst, wenn Spielerliste geändert wird.
     * @param playerList
     */
    public void playerListChanged(List<Player> playerList){
        game.getPlayerManager().setPlayerList(playerList);
    }

    public void roomListChanged(List<Room> roomList){
        MenueDrawer.game.getRoomManager().setRoomList(roomList);
        map.update();
    }

    public void pauseIsPressed(boolean paused){
        if(paused){
            menueSetter(pause);
            drawerToggle.setDrawerIndicatorEnabled(false);

            //TODO Name von pause-drücker in PauseActivity einfügen
        }else if (!paused){
            menueSetter(map);
            drawerToggle.setDrawerIndicatorEnabled(true);
        }
    }

    public void setTimerOnPause(String status){
        switch (status){
            case "pause":
                if(myTurn)
                    timer.pause();
                break;
            case "resume":
                if(myTurn)
                    timer.resume();
                break;
        }
    }

    public void aktivePlayerChanged(double activePlayer){
        int activePlayerInt = (int)activePlayer;
        if(activePlayerInt == whoAmI && !myTurn) {
            myTurn = true;
            Intent intent = getIntent();
            intent.putExtra("myTurn", myTurn);
            intent.putExtra("GAME", game);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
            finish();
        }else{
            String toolbarText = " " + getResources().getString(R.string.toolbar_text);
            setTitle(game.getPlayerManager().getPlayerList().get(activePlayerInt).getName() + " " + toolbarText);
        }
    }

    public void onIndictPositive(DialogFragment dialog){
        game.setProsecutionNotify(true);
        stb.updateData("prosecutionNotify", true);
        stb.sendData("prosecutionIsPlaced", false);
        timer.getTimer().cancel();
        final int VALUE = 23;
        final Intent intent = new Intent(this, ProsecutionWaitingForPlayers.class);
        startActivityForResult(intent,VALUE);
    }

    public void onIndictNegative(DialogFragment dialog){    }

    public void onBackPositive(DialogFragment dialog){
        this.finish();
        startActivity(new Intent(this, Startscreen.class));
    }

    public void onBackNegative(DialogFragment dialog){    }

    public void prosecutionNotify(){

        if(!myTurn){
            makeNotify("Eine Anklage wird erhoben!", "Antippen und in den Gruppenraum gehen", "");//TODO String-Ressource englisch deutsch
            DialogFragment prosecutionBroadcast = new PopUpIndictPlayerBroadcast();
            try {
                prosecutionBroadcast.show(this.getFragmentManager(), "PopUpIndictPlayerBroadcast");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void makeNotify(String title, String message, String message2){
        mBuilder.setBuilder(title, message + " "+ message2);
        mNotificationManager.notify(23,mBuilder.getBuilder().build());
    }

    private int suspectionRoom;
    public static String roomForCalling;
    private Suspection suspection;

    public void suspectionNotify(Suspection suspection) {
        this.suspection = suspection;
        this.suspection.setCallback(this);

        String ownName = game.getPlayerManager().getPlayerList().get(whoAmI).getName();

        if (suspection.getPlayer().equals(ownName) && !suspection.isPlayerCalled()) {
            makeNotify("Du wirst gerufen!", "Begib dich in Raum", suspection.getRoom());//TODO String-Ressource englisch deutsch
            for (Room r : game.getRooms()) {
                if (r.getName().equals(suspection.getRoom()))
                    suspectionRoom = (int) r.getQrCode();
            }
            roomForCalling = suspection.getRoom();
            Bundle args = new Bundle();
            args.putString("message", getResources().getString(R.string.popup_suspection_call_single_player_message));
            args.putString("title", getResources().getString(R.string.popup_suspection_call_single_player_title));

            DialogFragment suspectionCallPlayer = new PupUpSuspectionCallSinglePlayer();
            suspectionCallPlayer.setArguments(args);
            try {
                suspectionCallPlayer.show(this.getFragmentManager(), "PupUpSuspectionCallSinglePlayer");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (suspection.isPlayerCalled() && !suspection.isClueShown()) {
            int nextPlayer = suspection.getSuspectionNextPlayer().intValue();
            if (nextPlayer == whoAmI && !suspection.getSuspector().equals(ownName))
                suspection.whoHasClues(game.getPlayerManager().getPlayerList().get(whoAmI));

        } else if (suspection.isClueShown())
            suspection.informPlayer(game.getPlayerManager().getPlayerList().get(whoAmI));
        else if(suspectionEqualsSolution() || suspection.getSuspector().equals(ownName)){
            fcm.unbindSuspectionListeners();
            makeNotify("Ein Verdacht wurde geäußert!", "Niemand hat einen Hinweis gezeigt", suspection.getRoom());//TODO String-Ressource englisch deutsch
        }
    }

    public void nobodyHadShownClue(){
        fcm.unbindSuspectionListeners();
        makeNotify("Ein Verdacht wurde geäußert!", "Niemand hat einen Hinweis gezeigt", suspection.getRoom());//TODO String-Ressource englisch deutsch
    }

    public boolean suspectionEqualsSolution(){
        Solution solution = game.getSolution();
        if(solution.getMurderer().equals(suspection.getPlayer()) && solution.getRoom().equals(suspection.getRoom()) && solution.getWeapon().equals(suspection.getWeapon()))
            return true;
        else
            return false;
    }

    public void callPlayer(DialogFragment dialog){
        final Intent intent = new Intent(this, STUB_SCANNER.class);
        startActivityForResult(intent, 4711);
    }

    public void suspectionNextPlayer(){
        stb.updateData("suspectionNextPlayer", suspection.getSuspectionNextPlayer());
    }

    public void informPlayerWhoHasClue(ArrayList<String> existendClues){
        makeNotify("Ein Verdacht wurde geäußert", "Du musst einen Hinweis zeigen", "");//TODO String-Ressource englisch deutsch
        PopUpSuspectionInformPlayerWhoHasClue informPlayer = new PopUpSuspectionInformPlayerWhoHasClue();
        Bundle args = new Bundle();
        args.putStringArrayList("existendClues", existendClues);
        args.putString("message", getResources().getString(R.string.popup_suspection_have_clue_message));
        args.putString("title", getResources().getString(R.string.popup_suspection_have_clue_title));
        informPlayer.setArguments(args);

        try {
            informPlayer.show(this.getFragmentManager(), "PopUpSuspectionInformPlayerWhoHasClue");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showSuspectionResultBroadcast(){
        fcm.unbindSuspectionListeners();
        makeNotify("Ein Verdacht wurde geäußert!", "Antippen um das Ergebnis zu sehen", "");//TODO String-Ressource englisch deutsch
        PopupSuspectionShowPlayersTheResult resultToPlayers = new PopupSuspectionShowPlayersTheResult();
        Bundle args = new Bundle();
        args.putString("suspector", suspection.getSuspector());
        args.putString("clueOwner", suspection.getClueOwner());
        args.putString("name", suspection.getPlayer());
        args.putString("weapon", suspection.getWeapon());
        args.putString("room", suspection.getRoom());
        args.putString("title", getResources().getString(R.string.popup_suspection_result_broadcast_title));
        args.putString("message", getResources().getString(R.string.popup_suspection_result_broadcast_message));
        resultToPlayers.setArguments(args);

        try {
            resultToPlayers.show(this.getFragmentManager(), "PopupSuspectionShowPlayersTheResult");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void informSuspector(){
        fcm.unbindSuspectionListeners();
        makeNotify("Dir wird ein Hinweis gezeigt", "Antippen um das Ergebnis zu sehen", "");//TODO String-Ressource englisch deutsch
        PopUpShowSuspectorTheResult resultToSuspector = new PopUpShowSuspectorTheResult();
        Bundle args = new Bundle();
        args.putString("title", getResources().getString(R.string.popup_suspection_result_to_suspector_title));
        args.putString("clueOwner", suspection.getClueOwner());
        args.putString("clue", suspection.getClue());
        resultToSuspector.setArguments(args);

        try {
            resultToSuspector.show(this.getFragmentManager(), "PopUpShowSuspectorTheResult");
        }catch (Exception e){
            e.printStackTrace();
        }
        stb.updateData("suspectionNotify", false);
        stb.sendData("suspectionObject",null);
    }

    public void shownClueToSuspectionObject(DialogFragment dialog, String clueName){
        suspection.setClueShown(true);
        suspection.setClue(clueName);
        suspection.setClueOwner(game.getPlayerManager().getPlayerList().get(whoAmI).getName());
       //stb.updateData("suspectionObject", suspection);
        stb.sendData("suspectionObject/clue", suspection.getClue());
        stb.sendData("suspectionObject/clueOwner", suspection.getClueOwner());
        stb.sendData("suspectionObject/clueShown", true);
    }

    public void startScanForGrpRoom(DialogFragment dialog){
        final Intent intent = new Intent(this, STUB_SCANNER.class);
        startActivityForResult(intent, 42);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean allPlayersAtGrpRoom = false;

        switch(requestCode) {
            case 23:
                if (resultCode == Activity.RESULT_OK) { //wenn Activity korrekt zuende geführt wurde
                    allPlayersAtGrpRoom = data.getBooleanExtra(STUB_SCANNER.RESULT, false);
                }

                if (allPlayersAtGrpRoom)
                    menueSetter(indict);
                break;

            case 42:
                if (resultCode == Activity.RESULT_OK) {
                    int qrCode = data.getIntExtra(STUB_SCANNER.RESULT, 0);
                    if (qrCode == 29) {
                        game.getPlayerManager().getPlayerList().get(whoAmI).setActualRoom(game.getGrpRoom());
                        stb.updateData("playerList", MenueDrawer.game.getPlayerManager().getPlayerList().get(whoAmI));
                        menueSetter(prosecutionFromOtherPlayer);
                    } else
                        prosecutionNotify();
                } else {
                    prosecutionNotify();
                }
                break;
            case 4711:
                if (resultCode == Activity.RESULT_OK) {
                    int qrCode = data.getIntExtra(STUB_SCANNER.RESULT, 0);
                    if (qrCode == suspectionRoom) {
                        game.getPlayerManager().getPlayerList().get(whoAmI).setActualRoom(game.getGrpRoom());//TODO richtigen Raum setzen
                        suspection.setPlayerCalled(true);
                        try {
                            stb.updateData("playerList", MenueDrawer.game.getPlayerManager().getPlayerList().get(whoAmI));
                            stb.updateData("playerCalled", true);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    } else
                        prosecutionNotify();
                } else {
                    prosecutionNotify();
                }
                break;
        }
    }

    public void stopTimer(){
        timer.getTimer().cancel();
    }



    /*
        Auto-generated methods
 */

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

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

    @Override
    public void onDestroy(){
        super.onDestroy();

        if(timer != null)
            timer.getTimer().cancel();

        fcm.unbindListeners();
    }


    @Override
    protected void onResume() {
        super.onResume();

//        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
  //      notificationManager.cancelAll();
    }


}