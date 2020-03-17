package team.speckfamily.teddy.werewolf.start;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import team.speckfamily.teddy.werewolf.data.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class requestedGame {
    public static Map<Message, requestedGame> requestedGames = new HashMap<>();
    public List<Player> playerList = new ArrayList<>();
    public User admin;
    public Message message;
    private boolean confirmed = false;
    public requestedGame(Message message, User admin){
        this.admin      = admin;
        this.message    = message;
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
