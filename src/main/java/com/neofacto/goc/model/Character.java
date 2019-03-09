package com.neofacto.goc.model;

import lombok.Getter;

public enum Character {

    Prince(false),
    // not be able to attack.
    // can activate protection for few seconds.

    Libra(true),
    // attack: throw books.
    // ultimate attack: silence! -> slow down enemies.

    Deva(true),
    // attack: throw hooks.
    // ultimate attack: 404 -> she disappears for few seconds.

    Astra(true);
    // attack: throw mini-rockets.
    // ultimate attack: zero-gravity -> enemies will not be able to jump for few seconds.

    @Getter
    private boolean ableToAttack;

    private Character(boolean canAttack) {
        this.ableToAttack = canAttack;
    }

}
