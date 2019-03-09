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

    public static Game instantiate() {
        final Game game = Game.builder().build();
        game.addTeam("team1");
        game.addTeam("team2");
        return game;
    }

    public void addTeam(String teamName) {
        if (teams.size() < 2) {
            teams.put(teamName, Team.builder().name(teamName).build());
        }
    }

    public void addPlayer(Player player, String teamName, SocketIOClient client) {
        if (teams.containsKey(teamName)) {
            Team team = teams.get(teamName);
            if (!team.addPlayer(player, client.getSessionId())) {
                client.sendEvent(GameServer.EVENT_ERROR, "Team " + teamName + " already has character " + player.getCharacter().name());
            }
            teams.put(teamName, team);
        } else {
            client.sendEvent(GameServer.EVENT_ERROR, "Unknown team: " + teamName);
        }
    }

}
