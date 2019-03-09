package com.neofacto.goc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Player {

    public int FULL_JAUGE = 100;

    private String name;
    private Character character;
    private int score = 0;  // increase with inflicted damages to others.
    private int damages = 0;
    private boolean protectionActive;

    public boolean canAttack(boolean ultimate) {
        return character.isAbleToAttack() && (!ultimate || (score == FULL_JAUGE));
    }

}
