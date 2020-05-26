package team.speckfamily.teddy.werewolf.game.players;

import net.dv8tion.jda.api.entities.User;
import team.speckfamily.teddy.werewolf.game.Vote;
import team.speckfamily.teddy.werewolf.game.midgame.Action;
import team.speckfamily.teddy.werewolf.game.midgame.ActionType;
import team.speckfamily.teddy.werewolf.game.midgame.Game;

import java.util.ArrayList;
import java.util.List;

public class Werewolf extends PlayerObject{
    public Werewolf(User user) {
        super(user);
    }

    @Override
    public List<Action> onAction(Game game) {
        return null;
    }

    @Override
    public List<Action> onDie(Game game) {
        return null;
    }

    @Override
    public List<Action> onFirstCall(Game game) {
        return null;
    }

    @Override
    public FractionName getName() {
        return FractionName.Werewolf;
    }
}
