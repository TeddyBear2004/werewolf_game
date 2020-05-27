package team.speckfamily.teddy.werewolf.game.players.logic;

import team.speckfamily.teddy.werewolf.game.Vote;
import team.speckfamily.teddy.werewolf.game.midgame.Action;
import team.speckfamily.teddy.werewolf.game.midgame.ActionType;
import team.speckfamily.teddy.werewolf.game.midgame.Game;
import team.speckfamily.teddy.werewolf.game.players.Player;
import team.speckfamily.teddy.werewolf.game.players.Werewolf;

import java.util.ArrayList;
import java.util.List;

public class WerewolfLogic extends LogicObject {
    @Override
    public List<Action> onAction(Game game) {
        List<Action> actions = new ArrayList<>();
        Vote vote = new Vote(Werewolf.class, game.getPlayers(), "Bitte wähle aus, wer nun sterben soll.", true, 2, false);
        Player player = vote.getVotedPlayer();
        if(player == null)
            player = game.getRandomPlayer();

        actions.add(new Action(ActionType.kill, player));
        return actions;

    }

    @Override
    public List<Action> onFirstCall(Game game) {
        return null;
    }

    @Override
    public List<Action> onDie(Game game) {
        return null;
    }
}
