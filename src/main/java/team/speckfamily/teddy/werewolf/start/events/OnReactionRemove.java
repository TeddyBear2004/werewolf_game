package team.speckfamily.teddy.werewolf.start.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import team.speckfamily.teddy.werewolf.data.Embed;
import team.speckfamily.teddy.werewolf.game.Game;
import team.speckfamily.teddy.werewolf.start.requestedGame;

import java.util.Objects;

public class OnReactionRemove extends ListenerAdapter {
    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event){
        if(Objects.requireNonNull(event.getUser()).isBot())return;
        event.getChannel().retrieveMessageById(event.getMessageId()).queue(message -> {
            if(!requestedGame.requestedGames.containsKey(message))return;
                switch (event.getReactionEmote().getEmoji()){
                    case "🐺":
                        EmbedBuilder builder = Embed.generate();
                        builder.addField("Leave", "You left the game", false);
                        event.getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(builder.build()).queue());
                        requestedGame.requestedGames.get(message).removeFromPlayerList(event.getUser());
                        break;
                    case "✅":
                        if(requestedGame.requestedGames.get(message).isAdmin(event.getUser())){
                            if(!requestedGame.requestedGames.get(message).isConfirmed())return;
                            if(requestedGame.requestedGames.get(message).getPlayerList().size() < 3){
                                builder = Embed.generate();
                                builder.addField("Not enough players!", "You may not start than less than 3 people.", false);
                                event.getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(builder.build()).queue());
                                return;
                            }

                            builder = Embed.generate();
                            builder.addField("Started!", "You have successfully started the game.", false);
                            event.getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(builder.build()).queue());
                            Game.runningGames.put(message, new Game(requestedGame.requestedGames.get(message).getPlayerList()));
                            requestedGame.requestedGames.remove(message);
                        }
                        break;
                }
        });
    }
}
