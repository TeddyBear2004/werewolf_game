package team.speckfamily.teddy.werewolf.start.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import team.speckfamily.teddy.werewolf.data.Embed;
import team.speckfamily.teddy.werewolf.start.requestedGame;

import java.util.Objects;

public class WerewolfCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase("?werewolf")) {
            EmbedBuilder builder = Embed.generate();
            builder.setTitle("Start a new Werewolf Game!");
            builder.addField("Join", "React with 🐺 to join the game!", false);
            builder.addField("Start", "React with ✅ to start the game!", false);

            event.getChannel().sendMessage(builder.build()).queue(message -> {
                message.addReaction("U+1F43A").queue();
                message.addReaction("U+2705").queue();
                requestedGame game = new requestedGame(message, Objects.requireNonNull(event.getMember()).getUser());
                requestedGame.requestedGames.put(message, game);
            });
        }
    }
}
