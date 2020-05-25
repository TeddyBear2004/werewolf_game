package team.speckfamily.teddy.werewolf.game;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

abstract public class PlayerObject {
    private final PlayerType playerType;
    private final Member member;
    public PlayerObject(Member member, PlayerType playerType){
        this.member = member;
        this.playerType = playerType;

    }
    public PlayerType getPlayerType() {
        return playerType;
    }

    public Member getMember() {
        return member;
    }
    public User getUser(){
        return member.getUser();
    }
}
