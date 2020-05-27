package team.speckfamily.teddy.werewolf.game.players.logic;

import team.speckfamily.teddy.werewolf.game.Vote;
import team.speckfamily.teddy.werewolf.game.midgame.Action;
import team.speckfamily.teddy.werewolf.game.midgame.ActionType;
import team.speckfamily.teddy.werewolf.game.midgame.Game;
import team.speckfamily.teddy.werewolf.game.players.Player;
import team.speckfamily.teddy.werewolf.game.players.Witch;

import java.util.ArrayList;
import java.util.List;

public class WitchLogic extends LogicObject {
    @Override
    public List<Action> onAction(Game game) {
        List<Action> actions = new ArrayList<>();
        List<Player> witches = game.getPlayersFromFraction(Witch.class);
        if(witches.size() == 0)return null;
        Player p;
        if(game.witchCanHeal){
            p = new Vote(Witch.class, game.getDiedPlayers(), "", false, 1, true).getVotedPlayer();
            if(p != null){
                actions.add(new Action(ActionType.revive, p));
                game.witchCanHeal = false;
            }
        }
        if(game.witchCanKill){
            p = new Vote(Witch.class, game.getPlayers(), "", false, 1, false).getVotedPlayer();
            if(p != null){
                actions.add(new Action(ActionType.kill, p));
                game.witchCanKill = false;
            }
        }

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
