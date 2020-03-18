package team.speckfamily.teddy.werewolf.game;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import team.speckfamily.teddy.werewolf.data.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vote extends ListenerAdapter {
    private List<User> votingPlayers = new ArrayList<>();
    private List<Player> players;
    private PlayerType type;
    private Map<Player, Message> voteInfoMessages = new HashMap<>();

    public Vote(final PlayerType type, final List<Player> players, final String msg){
        this.type = type;
        players.forEach(player -> {
            if(type == player.getType()) {
                this.votingPlayers.add(player.getUser());
                player.getUser().openPrivateChannel().queue(privateChannel -> voteInfoMessages.put(player, privateChannel.sendMessage(msg).complete()));
            }
        });
        // x: 2 (You)
        // y: 1
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if(event.getChannelType().isGuild())return;
        if(!players.contains(Player.of(event.getAuthor())))return;
    }

    private List<Player> getPossibleVotees(Player player){
        List<Player> votees = new ArrayList<>();
        players.forEach(player1 -> {
            if(player.equals(player1))return;
            if(type == PlayerType.DORFBEWOHNER || player1.getType() != player.getType())
                votees.add(player1);
        });
        return votees;
    }
}
