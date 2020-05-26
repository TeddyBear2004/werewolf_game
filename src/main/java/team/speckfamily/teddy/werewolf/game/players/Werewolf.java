package team.speckfamily.teddy.werewolf.game.players;

import net.dv8tion.jda.api.entities.User;
import team.speckfamily.teddy.werewolf.game.midgame.Action;
import team.speckfamily.teddy.werewolf.game.midgame.Game;

import java.util.ArrayList;
import java.util.List;

public class Werewolf extends PlayerObject{
    private final List<Werewolf> werewolves = new ArrayList<>();
    public Werewolf(User user) {
        super(user);
    }
    public Werewolf() {
        super(null);
    }

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

    @Override
    public FractionName getName() {
        return FractionName.Werewolf;
    }
}
