package com.example.jendrik.moerder.GUI.OnGamingClasses;

import com.example.jendrik.moerder.GameObjekts.Player;
import com.example.jendrik.moerder.GameObjekts.Room;

import java.util.List;

/**
 * Created by Jendrik on 19.06.2016.
 */
public interface GameIsRunningCallback {
    void playerListChanged(List<Player> playerList);
    void roomListChanged(List<Room> roomList);
    void pauseIsPressed(boolean paused);
    void aktivePlayerChanged(double aktivePlayer);
}
