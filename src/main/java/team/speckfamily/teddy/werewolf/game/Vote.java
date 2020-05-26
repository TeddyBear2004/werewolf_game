package team.speckfamily.teddy.werewolf.game;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import team.speckfamily.teddy.werewolf.data.Embed;
import team.speckfamily.teddy.werewolf.Main;
import team.speckfamily.teddy.werewolf.game.players.PlayerObject;
import team.speckfamily.teddy.werewolf.game.players.Villiger;
import team.speckfamily.teddy.werewolf.game.players.Werewolf;

import java.util.*;
import java.util.concurrent.*;

public class Vote extends ListenerAdapter {
    private final List<User> votingPlayers = new ArrayList<>();
    private final List<PlayerObject> players;
    private final Class<? extends PlayerObject> type;
    private final Map<PlayerObject, Message> voteMessages = new HashMap<>();
    private final Map<PlayerObject, PlayerObject> votes = new HashMap<>();
    private final Map<PlayerObject, Integer> count = new HashMap<>();
    private final CountDownLatch done = new CountDownLatch(1);
    private PlayerObject deadPlayer = null;


    /**
     *
     * @param type the type who vote
     * @param players all players
     * @param msg the message which send to all players
     *
     */
    public Vote(Class<? extends PlayerObject> type, List<PlayerObject> players, String msg){
        Main.jda.addEventListener(this);
        this.type = type;
        this.players = players;
        this.refreshCount();

        players.forEach(player -> {
            if(type == player.getClass()) {
                this.votingPlayers.add(player.getUser());
                player.getUser().openPrivateChannel()
                        .queue(privateChannel ->
                                privateChannel.sendMessage(msg).queue());
            }
        });
        this.votingPlayers.forEach(user ->
                user.openPrivateChannel().queue(privateChannel -> privateChannel
                        .sendMessage(associatePlayerToNumberToString(PlayerObject.of(user)))
                        .queue(message1 -> voteMessages.put(PlayerObject.of(user), message1))));
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.isFromGuild())return;

        if(!players.contains(PlayerObject.of(event.getAuthor())))return;

        List<String> args = new LinkedList<>(Arrays.asList(event.getMessage().getContentRaw().split(" ")));
        args.removeIf(s -> s == null || s.equals(""));

        if(!isInt(args.get(0))){
            event.getChannel().sendMessage(Embed.generate("Die Nachricht muss mit einer Zahl starten.").build()).queue();
            return;
        }

        votes.put(PlayerObject.of(event.getAuthor()), associatePlayerToNumber(PlayerObject.of(event.getAuthor())).get(Integer.parseInt(args.get(0))));

        this.voteMessages.forEach((player, message1) -> message1.editMessage(associatePlayerToNumberToString(player)).queue());

        count.forEach((player, integer) -> {
            double percent = ((double)integer / votingPlayers.size());
            if(percent > 0.5){
                deadPlayer = player;
                done.countDown();
            }
        });
    }

    /**
     * @return the player who died
     */
    public PlayerObject getVotedPlayer(){
        try {
            done.await(2, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Main.jda.removeEventListener(this);
        return this.deadPlayer;
    }

    private void refreshCount(){
        votes.forEach((voter, player2) -> count.put(player2, (count.getOrDefault(player2, 0)+1)));
    }

    private Map<Integer, PlayerObject> associatePlayerToNumber(PlayerObject player){
        Map<Integer, PlayerObject> playerIntegerMap = new HashMap<>();

        players.forEach(player1 -> {
            if(player.equals(player1))return;
            if(this.type == Villiger.class || player.getClass() != player1.getClass())
                playerIntegerMap.put(playerIntegerMap.size()+1, player1);
        });
        return playerIntegerMap;
    }
    private String associatePlayerToNumberToString(PlayerObject player){
        refreshCount();
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
