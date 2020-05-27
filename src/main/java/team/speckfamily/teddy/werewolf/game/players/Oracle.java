package team.speckfamily.teddy.werewolf.game.players;

import net.dv8tion.jda.api.entities.User;
import team.speckfamily.teddy.werewolf.game.midgame.Action;
import team.speckfamily.teddy.werewolf.game.midgame.Game;

import java.util.List;

public class Oracle extends Player {
    public Oracle(User user){
        super(user, FractionName.Oracle);
    }
}
