package GameObjekts;

/**
 * Created by Jendrik on 22.03.2016.
 */
public class Suspection {
    private String player;
    private String room;
    private String weapon;
    private String suspector;
    private String cardOwner;

    public Suspection(String player, String room, String weapon, String suspector, String cardOwner){
        this.player=player;
        this.room=room;
        this.weapon=weapon;
        this.suspector=suspector;
        this.cardOwner = cardOwner;
    }

    

    public String getPlayer() {
        return player;
    }

    public String getRoom() {
        return room;
    }

    public String getWeapon() {
        return weapon;
    }

    public String getSuspector() {
        return suspector;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    

    

}
