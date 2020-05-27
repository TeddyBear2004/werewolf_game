package team.speckfamily.teddy.werewolf.game.start.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import team.speckfamily.teddy.werewolf.data.Embed;
import team.speckfamily.teddy.werewolf.game.midgame.Game;
import team.speckfamily.teddy.werewolf.game.start.RequestedGame;

import java.util.Objects;

public class OnReactionRemove extends ListenerAdapter {
    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event){
        if(Objects.requireNonNull(event.getUser()).isBot())return;
        event.getChannel().retrieveMessageById(event.getMessageId()).queue(message -> {
            if(!RequestedGame.requestedGames.containsKey(message))return;
                switch (event.getReactionEmote().getEmoji()){
                    case "🐺":
                        EmbedBuilder builder = Embed.generate();
                        builder.addField("Leave", "You left the game", false);
                        event.getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(builder.build()).queue());
                        RequestedGame.requestedGames.get(message).removeFromPlayerList(event.getUser());
                        break;
                    case "✅":
                        if(RequestedGame.requestedGames.get(message).isAdmin(event.getUser())){
                            if(!RequestedGame.requestedGames.get(message).isConfirmed())return;
                            if(RequestedGame.requestedGames.get(message).getPlayerList().size() < 2){
                                builder = Embed.generate();
                                builder.addField("Not enough players!", "You may not start than less than 3 people.", false);
                                event.getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(builder.build()).queue());
                                return;
                            }

                            builder = Embed.generate();
                            builder.addField("Started!", "You have successfully started the game.", false);
                            event.getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(builder.build()).queue());
                            Game.runningGames.put(message, new Game(RequestedGame.requestedGames.get(message).getPlayerList()));
                            RequestedGame.requestedGames.remove(message);
                        }
                        break;
                }
        });
    }
}
