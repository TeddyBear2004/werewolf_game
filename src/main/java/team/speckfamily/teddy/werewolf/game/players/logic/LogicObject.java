package team.speckfamily.teddy.werewolf.game.players.logic;

import team.speckfamily.teddy.werewolf.game.midgame.Action;
import team.speckfamily.teddy.werewolf.game.midgame.Game;

import java.util.List;

public abstract class LogicObject {
    public abstract List<Action> onAction(Game game);
    public abstract List<Action> onFirstCall(Game game);
    public abstract List<Action> onDie(Game game);

}
