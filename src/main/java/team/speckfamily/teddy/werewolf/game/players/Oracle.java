package team.speckfamily.teddy.werewolf.game.players;

import net.dv8tion.jda.api.entities.User;

public class Oracle extends Player {
    public Oracle(User user){
        super(user, FractionName.Oracle);
    }
}
