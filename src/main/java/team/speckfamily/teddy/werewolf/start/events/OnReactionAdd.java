package team.speckfamily.teddy.werewolf.start.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import team.speckfamily.teddy.werewolf.data.Embed;
import team.speckfamily.teddy.werewolf.start.RequestedGame;

import java.util.Objects;

public class OnReactionAdd extends ListenerAdapter {
    @Override
    public void onMessageReactionAdd(final MessageReactionAddEvent event) {
        if(Objects.requireNonNull(event.getUser()).isBot())return;
        event.getChannel().retrieveMessageById(event.getMessageId()).queue(message -> {
            if(!RequestedGame.requestedGames.containsKey(message))return;

            switch (event.getReactionEmote().getEmoji()){
                case "🐺":
                    RequestedGame.requestedGames.get(message).addToPlayerList(event.getUser());
                    event.getUser().openPrivateChannel().queue(privateChannel -> {
                        EmbedBuilder builder = Embed.generate();
                        builder.addField("Joined!", "You joined the game!", false);
                        privateChannel.sendMessage(builder. build()).queue();
                    });
                    break;
                case "✅":
                    if(RequestedGame.requestedGames.get(message).isAdmin(event.getUser())){
                        EmbedBuilder builder = Embed.generate();
                        builder.addField("Confirm!", "Remove the ✅ Emoji to confirm to start.", false);
                        event.getUser().openPrivateChannel().queue(privateChannel ->
                                privateChannel.sendMessage(builder.build()).queue());
                        RequestedGame.requestedGames.get(message).setConfirmed();
                    }else{
                        event.getReaction().removeReaction().queue();
                        event.getUser().openPrivateChannel().queue(privateChannel -> {
                            EmbedBuilder builder = Embed.generate();
                            builder.addField("Error", "You can't start this game", false);
                            privateChannel.sendMessage(builder.build()).queue();
                        });
                    }
                    break;
            }
        });
    }
}