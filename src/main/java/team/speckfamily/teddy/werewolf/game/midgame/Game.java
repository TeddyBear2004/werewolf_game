package team.speckfamily.teddy.werewolf.game.midgame;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import team.speckfamily.teddy.werewolf.data.Embed;
import team.speckfamily.teddy.werewolf.game.players.Oracle;
import team.speckfamily.teddy.werewolf.game.Vote;
import team.speckfamily.teddy.werewolf.game.players.PlayerObject;
import team.speckfamily.teddy.werewolf.game.players.Villiger;
import team.speckfamily.teddy.werewolf.game.players.Werewolf;

import java.util.*;

public class Game {
    public static Map<Message, Game> runningGames = new HashMap<>();
    private final List<PlayerObject> players = new ArrayList<>();

    public Game(List<User> users){
        int sehAnzahl = 1;
        int wereAnzahl = users.size()/6;
        if(wereAnzahl == 0)
            wereAnzahl = 1;

        while (users.size() > 0){
            User p = users.get((int)(Math.random()*users.size()));
            if(sehAnzahl > 0){
                this.players.add(new Oracle(p));
                sehAnzahl--;
            }else if(wereAnzahl > 0){
                this.players.add(new Werewolf(p));
                wereAnzahl--;
            }else
                this.players.add(new Villiger(p));

            users.remove(p);
        }

        this.players.forEach(player -> player.getUser().openPrivateChannel().queue(privateChannel -> {
            EmbedBuilder embedBuilder = Embed.generate();
            embedBuilder.addField("Your role", String.format("You are a %s", player.getName()), false);
            privateChannel.sendMessage(embedBuilder.build()).queue();
        }));
        Vote vote;

        while (getWinnerFraction() == null) {

            vote = new Vote(Werewolf.class, players, "Bitte wähle aus, wer nun sterben soll.");
            final PlayerObject lastDeadPlayer = vote.getVotedPlayer();

            if (lastDeadPlayer != null) {
                players.remove(lastDeadPlayer);
                lastDeadPlayer.getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(Embed.generate("Du bist ausgeschieden.").build()).queue());
                players.forEach(player -> player.getUser()
                        .openPrivateChannel().queue(privateChannel ->
                                privateChannel.sendMessage(Embed.generate(
                                        lastDeadPlayer.getUser().getName() + " ist ausgeschieden.").build()).queue()));

            }else {}//todo pick random
        }

        String winnerFraction = getWinnerFraction();
        this.players.forEach(player -> player.getUser().openPrivateChannel().queue(privateChannel ->
                privateChannel.sendMessage(Embed.generate(
                        "Winner!", "The " + winnerFraction + " won this game.").build()).queue()));
    }


    String getWinnerFraction(){
        int werAnzahl = 0;
        for(PlayerObject player : players)
            if (player.getClass() == Werewolf.class)
                werAnzahl++;
        if(werAnzahl == 0)
            return Villiger.of(null).getName();
        else if(werAnzahl == players.size())
            return Werewolf.of(null).getName();
        return null;
    }
}