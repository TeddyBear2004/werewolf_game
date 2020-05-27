package team.speckfamily.teddy.werewolf.game.midgame;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import team.speckfamily.teddy.werewolf.data.Embed;
import team.speckfamily.teddy.werewolf.game.Vote;
import team.speckfamily.teddy.werewolf.game.players.*;
import team.speckfamily.teddy.werewolf.game.players.logic.LogicObject;
import team.speckfamily.teddy.werewolf.game.players.logic.OracleLogic;
import team.speckfamily.teddy.werewolf.game.players.logic.VilligerLogic;
import team.speckfamily.teddy.werewolf.game.players.logic.WerewolfLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    public static Map<Message, Game> runningGames = new HashMap<>();
    private final List<Player> players = new ArrayList<>();
    public boolean witchCanHeal = true;
    public boolean witchCanKill = true;

    private Player mayor;
    private List<Player> diedPlayers = new ArrayList<>();

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

        List<LogicObject> fractionList = new ArrayList<>();
        fractionList.add(new WerewolfLogic());
        fractionList.add(new OracleLogic());

        fractionList.add(new VilligerLogic());          //Villiger are always the last one


        //pregame
        for (LogicObject logicObject : fractionList)
            convertAction(logicObject.onFirstCall(this));

        while (getWinnerFraction() == null) {
            //night
            for (LogicObject logicObject : fractionList)
                convertAction(logicObject.onAction(this));

            broadcast("The following players were killed that night: \n" + getDiedPlayers());

            if(getWinnerFraction() != null)
                break;

            //day
            if(mayor == null || !players.contains(mayor))
                mayor = voteMayor();

            Player hangman = voteHangMan();
            diedPlayers.add(hangman);
            players.remove(hangman);
        }

        FractionName winnerFraction = getWinnerFraction();
        this.players.forEach(player -> player.getUser().openPrivateChannel().queue(privateChannel ->
                privateChannel.sendMessage(Embed.generate(
                        "Winner!", "The " + winnerFraction + " team won this game.").build()).queue()));
    }

    FractionName getWinnerFraction(){
        int werAnzahl = 0;
        for(Player player : players)
            if (player.getClass() == Werewolf.class)
                werAnzahl++;

        if(werAnzahl == 0)
            return new Villiger(null).getName();
        else if(werAnzahl == players.size())
            return new Werewolf(null).getName();
        return null;
    }

    public List<Player> getPlayers() {
        return players;
    }
    public Player getRandomPlayer(){
        return players.get((int)(Math.random() * players.size()));
    }
    public List<Player> getPlayersFromFraction(final Class<? extends Player> clazz){
        List<Player> players = new ArrayList<>();

        for (Player player : this.players)
            if(player.getClass() == clazz)
                players.add(player);

        return players;
    }

    public void broadcast(String msg, Class<? extends Player> clazz){
        players.forEach(player -> {
            if(player.getClass() == clazz || Player.class == clazz)
                player.getUser().openPrivateChannel().queue(privateChannel ->
                        privateChannel.sendMessage(msg).queue());
        });
    }
    public void broadcast(String msg){
        broadcast(msg, Player.class);
    }

    private void convertAction(List<Action> actions){
        if(actions == null)return;
        actions.forEach(action -> {
            if(action.getType() == null)return;

            switch (action.getType()){
                case kill:
                    diedPlayers.add(action.getPlayer());
                    break;
                case love:
                    //todo amor
                    break;
                default:
                    break;
            }
        });
    }

    private Player voteMayor() {
        Vote vote = new Vote(Player.class, getPlayers(), "Who should be the mayor?", false, 2);
        return vote.getVotedPlayer();
    }
    private Player voteHangMan(){
        Vote vote = new Vote(Player.class, getPlayers(), "Who is a werewolf and should be hanged?", false, 2);
        Player p = vote.getVotedPlayer();
        broadcast("The player " + p.getUser().getName() + " was killed", Werewolf.class);
        return p;

    }

    private String getDiedPlayers(){
        StringBuilder builder = new StringBuilder();
        diedPlayers.forEach(players::remove);
        diedPlayers.forEach(player -> builder.append(player.getUser().getName()).append(", "));
        if(builder.length() >= 2)return "nobody";

        builder.replace(builder.length()-2, builder.length(), "");
        diedPlayers = new ArrayList<>();
        return builder.toString();
    }
}
