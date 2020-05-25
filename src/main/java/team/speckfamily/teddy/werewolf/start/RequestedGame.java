package team.speckfamily.teddy.werewolf.start;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestedGame {
    public static Map<Message, RequestedGame> requestedGames = new HashMap<>();
    public List<User> userList = new ArrayList<>();
    public User admin;
    public Message message;
    private boolean confirmed = false;

    public RequestedGame(Message message, User admin){
        this.admin      = admin;
        this.message    = message;
    }
    public List<User> getPlayerList() {
        return userList;
    }
    public void addToPlayerList(User user){
        userList.add(user);
    }
    public void removeFromPlayerList(User user){
        userList.remove(user);
    }
    public boolean isAdmin(User user){
        return admin == user;
    }
    public void setConfirmed() {
        confirmed = true;
    }
    public boolean isConfirmed() {
        return confirmed;
    }
}
