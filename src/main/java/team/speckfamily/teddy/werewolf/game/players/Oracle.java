package team.speckfamily.teddy.werewolf.game.players;

import net.dv8tion.jda.api.entities.User;
import team.speckfamily.teddy.werewolf.game.midgame.Action;
import team.speckfamily.teddy.werewolf.game.midgame.Game;

public class Oracle extends PlayerObject{
    public Oracle(User user) {
        super(user);
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
    public String getName() {
        return "Oracle";
    }
}