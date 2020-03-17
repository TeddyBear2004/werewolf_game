package team.speckfamily.teddy.werewolf.game;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import team.speckfamily.teddy.werewolf.data.Embed;
import team.speckfamily.teddy.werewolf.data.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Game {
    public static Map<Message, Game> runningGames = new HashMap<>();
    private Map<Player, PlayerType> players = new HashMap<>();

    public Game(List<Player> players){
        int sehAnzahl = 1;
        int wereAnzahl = players.size()/6;
        if(wereAnzahl == 0)
            wereAnzahl = 1;

        while (players.size() > 0){
            Player p = players.get((int)(Math.random()*players.size()));
            if(sehAnzahl > 0){
                this.players.put(p, PlayerType.SEHER);
                sehAnzahl--;
            }else if(wereAnzahl > 0){
                this.players.put(p, PlayerType.WERWOLF);
                wereAnzahl--;
            }else {
                this.players.put(p, PlayerType.DORFBEWOHNER);
            }
            players.remove(p);
        }
        this.players.forEach((player, playerType) -> player.getUser().openPrivateChannel().queue(privateChannel -> {
            EmbedBuilder embedBuilder = Embed.generate();
            embedBuilder.addField("Your role", String.format("You are a %s", playerType), false);
            privateChannel.sendMessage(embedBuilder.build()).queue();
        }));
        while (isRunning()){
            //TODO Insert Game Process here
        }
    }
    boolean isRunning(){
        int werAnzahl = 0;
        Collection<PlayerType> collection;
        collection = players.values();
        for(PlayerType playerType : collection){
            if (playerType == PlayerType.WERWOLF) {
                werAnzahl++;
            }
        }
        if(werAnzahl == 0){
            return false;
        }else return werAnzahl != players.size();
    }
    void broadcast(final MessageEmbed.Field field){
        players.forEach((player, playerType) -> player.getUser().openPrivateChannel().queue(
                privateChannel -> privateChannel.sendMessage(Embed.generate().addField(field).build()).queue()));
    }
    void broadcast(final MessageEmbed.Field field, int timeInSec){
        players.forEach((player, playerType) -> player.getUser().openPrivateChannel().queue(
                privateChannel -> privateChannel.sendMessage(Embed.generate().addField(field).build())
                        .queueAfter(timeInSec, TimeUnit.SECONDS)));
    }
    void broadcast(final MessageEmbed.Field field, PlayerType type){
        players.forEach((player, playerType) -> {
            if(playerType == type) {
                player.getUser().openPrivateChannel().queue(
                        privateChannel -> privateChannel.sendMessage(Embed.generate().addField(field).build()).queue());
            }
        });
    }
    void broadcast(final MessageEmbed.Field field, PlayerType type, int timeInSec){
        players.forEach((player, playerType) -> {
            if(playerType == type) {
                player.getUser().openPrivateChannel().queue(
                        privateChannel -> privateChannel.sendMessage(Embed.generate().addField(field).build())
                                .queueAfter(timeInSec, TimeUnit.SECONDS));
            }
        });
    }
}
