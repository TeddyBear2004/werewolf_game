package team.speckfamily.teddy.werewolf.game.players;

import net.dv8tion.jda.api.entities.User;

public class Werewolf extends Player {
    public Werewolf(User user) {
        super(user, FractionName.Werewolf);
    }
}
