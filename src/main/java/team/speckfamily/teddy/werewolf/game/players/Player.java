package team.speckfamily.teddy.werewolf.game.players;

import net.dv8tion.jda.api.entities.User;
import team.speckfamily.teddy.werewolf.game.midgame.Action;
import team.speckfamily.teddy.werewolf.game.midgame.Game;

import java.util.List;
import java.util.Objects;

public abstract class Player {
    private final User user;
    private final FractionName fractionName;
    public abstract List<Action> onDie(Game game);

    public Player(User user, FractionName fractionName){
        this.user = user;
        this.fractionName = fractionName;
    }

    public User getUser() {
        return user;
    }
    public static Player of(User user){
        return new Player(user, FractionName.NotSet){
            @Override
            public List<Action> onDie(Game game) {
                return null;
            }
        };
    }

    public FractionName getName(){
        return fractionName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        return Objects.equals(user, ((Player) o).getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }


}
