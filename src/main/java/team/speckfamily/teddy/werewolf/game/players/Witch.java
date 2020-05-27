package team.speckfamily.teddy.werewolf.game.players;

import net.dv8tion.jda.api.entities.User;

public class Witch extends Player{
    public Witch(User user) {
        super(user, FractionName.Witch);
    }
}
