package team.speckfamily.teddy.werewolf.game.midgame;


import team.speckfamily.teddy.werewolf.game.players.PlayerObject;

public class Action {
    private final ActionType type;
    private final PlayerObject playerObject;
    public Action(ActionType type, PlayerObject playerObject){
        this.playerObject = playerObject;
        this.type = type;
    }

    public ActionType getType() {
        return type;
    }

    public PlayerObject getPlayerObject() {
        return playerObject;
    }
}
