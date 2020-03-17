package team.speckfamily.teddy.werewolf.start;

import net.dv8tion.jda.api.entities.User;
import team.speckfamily.teddy.werewolf.data.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class requestedGame {
    public static Map<String, requestedGame> requestedGames = new HashMap<>();
    private List<Player> playerList;
    public User admin;
    public String id;
    private boolean confirmed = false;
    public requestedGame(String channel_id, User user){
        admin = user;
        playerList = new ArrayList<>();
        id = channel_id;
    }
    public List<Player> getPlayerList() {
        return playerList;
    }
    public void addToPlayerList(User user){
        playerList.add(new Player(user));
    }
    public void setConfirmed() {
        confirmed = true;
    }
    public boolean isConfirmed() {
        return confirmed;
    }
}
