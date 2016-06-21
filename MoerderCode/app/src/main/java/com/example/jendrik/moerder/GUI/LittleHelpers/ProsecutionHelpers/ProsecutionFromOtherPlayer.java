package com.example.jendrik.moerder.GUI.LittleHelpers.ProsecutionHelpers;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jendrik.moerder.GUI.OnGamingClasses.LooseScreen;
import com.example.jendrik.moerder.GUI.OnGamingClasses.MenueDrawer;
import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Jendrik on 21.06.2016.
 */
public class ProsecutionFromOtherPlayer extends Fragment {
    private View fragLayoutV;
    private Game game;
    private LayoutInflater inflater;
    private ViewGroup container;
    private String gameName;

    private DatabaseReference prosecutionIsPlacedReference;
    private DatabaseReference playerWinsReference;
    private ValueEventListener prosecutionIsPlacedListener;
    private ValueEventListener playerWinsListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.inflater = inflater;
        this.container = container;
        fragLayoutV = inflater.inflate(R.layout.prosecution_waiting_for_placement, container, false);

        game = MenueDrawer.game;
        this.gameName = game.getGameName();

        prosecutionIsPlacedListener();

        return fragLayoutV;
    }

    public void prosecutionIsPlacedListener(){
        prosecutionIsPlacedListener = prosecutionListener();
        prosecutionIsPlacedReference = FirebaseDatabase.getInstance().getReference().child("games").child(gameName);
        prosecutionIsPlacedReference.child("prosecutionIsPlaced").addValueEventListener(prosecutionIsPlacedListener);
    }

    private ValueEventListener prosecutionListener(){
        ValueEventListener ve = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean prosecutionIsPlaced = dataSnapshot.getValue(Boolean.class);
                if(prosecutionIsPlaced)
                    playerWinsListener();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };
        return ve;
    }

    public void playerWinsListener(){
        playerWinsListener = makePlayerWinsListener();
        playerWinsReference = FirebaseDatabase.getInstance().getReference().child("games").child(gameName);
        playerWinsReference.child("playerWins").addValueEventListener(playerWinsListener);
    }

    private ValueEventListener makePlayerWinsListener(){
        ValueEventListener ve = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean playerWins = dataSnapshot.getValue(Boolean.class);
                if(playerWins) {
                    Intent intent = new Intent(getActivity(), LooseScreen.class);
                    startActivity(intent);
                    getActivity().finish();
                }else{
                    TextView tv = (TextView) getActivity().findViewById(R.id.tvWaitingForProsecution);
                    tv.setText(R.string.prosecutionWasWrong);
                }
                unbindListeners();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        return ve;
    }

    private void unbindListeners() {
        prosecutionIsPlacedReference.removeEventListener(prosecutionIsPlacedListener);
        playerWinsReference.removeEventListener(playerWinsListener);
    }


}
