package team.speckfamily.teddy.werewolf.data;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class Embed {
    public static EmbedBuilder generate(){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.YELLOW);
        builder.setAuthor("Werewolf", "http://www.speckfamily.team");
        builder.setTitle("Werewolf");
        builder.setFooter("Game created by T3ddy#2064");
        return builder;
    }
    public static EmbedBuilder generate(String title){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.YELLOW);
        builder.setAuthor("Werewolf", "http://www.speckfamily.team");
        builder.setTitle(title);
        builder.setFooter("Game created by T3ddy#2064");
        return builder;
    }
    public static EmbedBuilder generate(String title, String description){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.YELLOW);
        builder.setAuthor("Werewolf", "http://www.speckfamily.team");
        builder.setTitle(title);
        builder.setDescription(description);
        builder.setFooter("Game created by T3ddy#2064");
        return builder;
    }
}
