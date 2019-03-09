package com.neofacto.goc.model;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.neofacto.goc.GameServer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j(topic = "goc")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Game {

    public static final String EVENT_READY = "ready";
    public static final String EVENT_TIME = "time";
    public static final String EVENT_END = "end";
    public static final String EVENT_PLAYER_ADDED = "playerAdded";

    private Timer timer = new Timer();

    @Builder.Default
    private int remainingTime = 150;  // in ms.

    @Builder.Default
    private Map<String, Team> teams = new HashMap<>();

    public static Game instantiate() {
        final Game game = Game.builder().build();
        game.addTeam("team1");
        game.addTeam("team2");
        return game;
    }

    public boolean isReady() {
        if (remainingTime == 0) {
            return false;
        }
        if (teams.size() < 2) {
            return false;
        }
        for (Team team : teams.values()) {
            if (team.getMembers().size() < 1/*Character.values().length*/) {
                return false;
            }
        }
        return true;
    }

    public void start(SocketIOServer server) {
        if (isReady()) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    remainingTime--;
                    if (remainingTime == 0) {
                        timer.cancel();
                        timer.purge();
                        server.getBroadcastOperations().sendEvent(EVENT_END, true);
                        log.debug("End of game.");
                    }
                    server.getBroadcastOperations().sendEvent(EVENT_TIME, remainingTime);
                }
            }, 0, 1000);
        }
    }

    public void addTeam(String teamName) {
        if (teams.size() < 2) {
            teams.put(teamName, Team.builder().name(teamName).build());
        }
    }

    public void addPlayer(Player player, String teamName, SocketIOClient client) {
        if (teams.containsKey(teamName)) {
            Team team = teams.get(teamName);
            Player addedPlayer = team.addPlayer(player, client.getSessionId());
            if (addedPlayer == null) {
                client.sendEvent(GameServer.EVENT_ERROR, "Team " + teamName + " already has character " + player.getCharacter().name());
            } else {
                client.sendEvent(EVENT_PLAYER_ADDED, addedPlayer);
            }
            teams.put(teamName, team);
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

    public Team getPlayerTeam(String playerName) {
        for (Team team : teams.values()) {
            for (Player player : team.getMembers().values()) {
                if (player.getName().equals(playerName)) {
                    return team;
                }
            }
        }
        return null;
    }

}
