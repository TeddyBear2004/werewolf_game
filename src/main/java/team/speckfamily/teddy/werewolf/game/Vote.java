package team.speckfamily.teddy.werewolf.game;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import team.speckfamily.teddy.werewolf.data.Embed;
import team.speckfamily.teddy.werewolf.data.Player;

import java.util.*;

public class Vote extends ListenerAdapter {
    private final List<User> votingPlayers = new ArrayList<>();
    private final List<Player> players;
    private final PlayerType type;
    private final Map<Player, Message> voteMessages = new HashMap<>();
    private final Map<Player, Player> votes = new HashMap<>();
    private final String message;
    private final Map<Player, Integer> count = new HashMap<>();

    /**
     *
     * @param type the type who vote
     * @param players all players
     * @param msg the message which send to all players
     *
     */
    public Vote(PlayerType type, List<Player> players, String msg){
        this.type = type;
        this.players = players;
        this.message = msg;
        this.refreshCount();

        players.forEach(player -> {
            if(type == player.getType()) {
                this.votingPlayers.add(player.getUser());
                player.getUser().openPrivateChannel()
                        .queue(privateChannel ->
                                privateChannel.sendMessage(msg).queue());
            }
        });
        this.votingPlayers.forEach(user ->
                user.openPrivateChannel().queue(privateChannel -> privateChannel
                        .sendMessage(associatePlayerToNumberToString(Player.of(user)))
                        .queue(message1 -> voteMessages.put(Player.of(user), message1))));
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.isFromGuild())return;
        if(!players.contains(Player.of(event.getAuthor())))return;

        List<String> args = new LinkedList<>(Arrays.asList(event.getMessage().getContentRaw().split(" ")));
        args.removeIf(s -> s == null || s.equals(""));

        if(!isInt(args.get(0))){
            event.getChannel().sendMessage(Embed.generate("Die Nachricht muss mit einer Zahl starten.").build()).queue();
            return;
        }

        votes.put(Player.of(event.getAuthor()), associatePlayerToNumber(Player.of(event.getAuthor())).get(Integer.parseInt(args.get(0))));

        this.voteMessages.forEach((player, message1) -> message1.editMessage(associatePlayerToNumberToString(player)).queue());

        count.forEach((player, integer) -> {
            double percent = ((double)integer / votingPlayers.size());
            if(percent > 50){
                //todo Spieler scheidet aus
            }
        });
    }

    private void refreshCount(){
        votes.forEach((voter, player2) -> count.put(player2, count.getOrDefault(player2, 0)+1));
    }

    private Map<Integer, Player> associatePlayerToNumber(Player player){
        Map<Integer, Player> playerIntegerMap = new HashMap<>();

        players.forEach(player1 -> {
            if(player.equals(player1))return;
            if(this.type == PlayerType.DORFBEWOHNER || player.getType() != player1.getType())
                playerIntegerMap.put(playerIntegerMap.size()+1, player1);
        });
        return playerIntegerMap;
    }

    private String associatePlayerToNumberToString(Player player){
        StringBuilder msg = new StringBuilder();
        associatePlayerToNumber(player).forEach((integer, player1) -> msg.append(integer).append(" : ").append(player1.getUser().getName()).append(" [votes] ").append(count.getOrDefault(player1, 0)).append("\n"));
        msg.append(0).append(" : ").append(player.getUser().getName()).append(" [votes] ").append(count.getOrDefault(player, 0));
        return msg.toString();
    }
    private boolean isInt(String s){
        try {
            Integer.parseInt(s);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
}
