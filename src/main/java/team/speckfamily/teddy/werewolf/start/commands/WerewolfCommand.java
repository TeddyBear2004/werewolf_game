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
            builder.addField("Join", "React with \uD83D\uDC3A to join the game!", false);
            builder.addField("Start", "React with ✅ to start the game!", false);

            event.getChannel().sendMessage(builder.build()).queue(message1 -> {
                message1.addReaction("U+1F43A").queue();
                message1.addReaction("U+2705").queue();
                requestedGame game = new requestedGame(message1.getId(), Objects.requireNonNull(event.getMember()).getUser());
                requestedGame.requestedGames.put(message1.getId(), game);
            });
        }
    }
}
