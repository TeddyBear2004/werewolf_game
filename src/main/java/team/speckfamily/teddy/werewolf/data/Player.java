package team.speckfamily.teddy.werewolf.data;

import net.dv8tion.jda.api.entities.User;
import team.speckfamily.teddy.werewolf.game.PlayerType;

import java.util.Objects;

public class Player {
    private final User user;
    private final PlayerType type;
    public Player(User user, PlayerType type){
        this.user = user;
        this.type = type;
    }

    public PlayerType getType() {
        return type;
    }

    public User getUser() {
        return user;
    }
    public static Player of(User user){
        return new Player(user, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(user, player.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}
