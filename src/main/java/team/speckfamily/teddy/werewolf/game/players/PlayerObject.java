package team.speckfamily.teddy.werewolf.game.players;

import net.dv8tion.jda.api.entities.User;
import team.speckfamily.teddy.werewolf.game.midgame.Action;
import team.speckfamily.teddy.werewolf.game.midgame.Game;

import java.util.Objects;

public abstract class PlayerObject {
    private final User user;

    public PlayerObject(User user){
        this.user = user;

    }

    public abstract Action onAction(Game game);
    public abstract Action onDie(Game game);
    public abstract Action onFirstCall(Game game);

    public User getUser() {
        return user;
    }
    public static PlayerObject of(User user){
        return new PlayerObject(user) {
            @Override
            public Action onAction(Game game) {
                return null;
            }

            @Override
            public Action onDie(Game game) {
                return null;
            }

            @Override
            public Action onFirstCall(Game game) {
                return null;
            }
        };
    }

    public FractionName getName(){
        return FractionName.NotSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerObject)) return false;
        return Objects.equals(user, ((PlayerObject) o).getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }


}
