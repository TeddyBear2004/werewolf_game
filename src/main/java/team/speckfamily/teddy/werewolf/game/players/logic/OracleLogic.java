package team.speckfamily.teddy.werewolf.game.players.logic;

import team.speckfamily.teddy.werewolf.data.Embed;
import team.speckfamily.teddy.werewolf.game.Vote;
import team.speckfamily.teddy.werewolf.game.midgame.Action;
import team.speckfamily.teddy.werewolf.game.midgame.Game;
import team.speckfamily.teddy.werewolf.game.players.Oracle;
import team.speckfamily.teddy.werewolf.game.players.Player;

import java.util.List;

public class OracleLogic extends LogicObject {
    @Override
    public List<Action> onAction(Game game) {
        List<Player> oracles = game.getPlayersFromFraction(Oracle.class);
        if(oracles.size() == 0)return null;

        Vote vote = new Vote(Oracle.class, game.getPlayers(), "Wessen Rolle möchtest du erfahren?", false);
        Player player = vote.getVotedPlayer();
        Player oracle = oracles.get(0);
        if(player != null)
            oracle.getUser().openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessage(Embed.generate(player.getName().toString()).build()).queue();
            });
        return null;

    }

    @Override
    public List<Action> onFirstCall(Game game) {
        return null;
    }
}
