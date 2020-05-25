package team.speckfamily.teddy.werewolf.game;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import team.speckfamily.teddy.werewolf.data.Embed;
import team.speckfamily.teddy.werewolf.data.Player;

import java.util.*;

public class Game {
    public static Map<Message, Game> runningGames = new HashMap<>();
    private final List<Player> players = new ArrayList<>();

    public Game(List<User> users){
        int sehAnzahl = 1;
        int wereAnzahl = users.size()/6;
        if(wereAnzahl == 0)
            wereAnzahl = 1;

        while (users.size() > 0){
            User p = users.get((int)(Math.random()*users.size()));
            if(sehAnzahl > 0){
                this.players.add(new Player(p, PlayerType.SEHER));
                sehAnzahl--;
            }else if(wereAnzahl > 0){
                this.players.add(new Player(p, PlayerType.WERWOLF));
                wereAnzahl--;
            }else {
                this.players.add(new Player(p, PlayerType.DORFBEWOHNER));
            }
            users.remove(p);
        }
        this.players.forEach(player -> player.getUser().openPrivateChannel().queue(privateChannel -> {
            EmbedBuilder embedBuilder = Embed.generate();
            embedBuilder.addField("Your role", String.format("You are a %s", player.getType()), false);
            privateChannel.sendMessage(embedBuilder.build()).queue();
        }));
        while (isRunning()){
            //TODO Insert Game Process here
        }
    }
    boolean isRunning(){
        int werAnzahl = 0;
        for(Player player : players){
            if (player.getType() == PlayerType.WERWOLF) {
                werAnzahl++;
            }
        }
        if(werAnzahl == 0){
            return false;
        }else return werAnzahl != players.size();
    }

}
