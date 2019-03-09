package com.neofacto.goc.model;

import com.corundumstudio.socketio.SocketIOClient;
import com.neofacto.goc.GameServer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Game {

    @Builder.Default
    private Map<String, Team> teams = new HashMap<>();

    public void addTeam(String teamName) {
        if (teams.size() < 2) {
            teams.put(teamName, Team.builder().build());
        }
    }

    public void addPlayer(Player player, String teamName, SocketIOClient client) {
        if (teams.containsKey(teamName)) {
            if (!teams.get(teamName).addPlayer(player, client.getSessionId())) {
                client.sendEvent(GameServer.EVENT_ERROR, "Team " + teamName + " already has character "
                        + player.getCharacter().name());
            }
        } else {
            client.sendEvent(GameServer.EVENT_ERROR, "Unknown team: " + teamName);
        }
    }

    public Team getPlayerTeam(SocketIOClient client) {
        for (Team team : teams.values()) {
            if (team.getMembers().get(client.getSessionId()) != null) {
                return team;
            }
        }
        return null;
    }

}
