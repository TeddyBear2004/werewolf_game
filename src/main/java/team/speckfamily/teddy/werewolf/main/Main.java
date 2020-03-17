package team.speckfamily.teddy.werewolf.main;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {
    public static JDA jda;
    public static void main(String[] args) throws LoginException {
        if(args.length == 0)
            System.exit(-1);
        jda = new JDABuilder(args[0])
                .build();
    }
}
