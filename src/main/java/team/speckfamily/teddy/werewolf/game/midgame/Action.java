package team.speckfamily.teddy.werewolf.game.midgame;


import team.speckfamily.teddy.werewolf.game.players.Player;

public class Action {
    private final ActionType type;
    private final Player player;
    public Action(ActionType type, Player player){
        this.player = player;
        this.type = type;
    }

    public ActionType getType() {
        return type;
    }

    public Player getPlayer() {
        return player;
    }
}
