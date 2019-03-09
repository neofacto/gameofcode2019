package com.neofacto.goc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Team {

    private String name;

    @Builder.Default
    private Map<UUID, Player> members = new HashMap<>();

    public boolean addPlayer(Player player, UUID uuid) {
        for (Player member : members.values()) {
            if (member.getCharacter().equals(player.getCharacter())) {
                return false;
            }
        }
        members.put(uuid, player);
        return true;
    }

}
