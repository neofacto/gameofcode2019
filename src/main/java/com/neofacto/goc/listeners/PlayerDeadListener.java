package com.neofacto.goc.listeners;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.neofacto.goc.model.Game;
import com.neofacto.goc.model.Player;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "goc")
public class PlayerDeadListener extends BaseEventListener<Player> {

    public static final String EVENT_DEAD = "deathEvent";

    public PlayerDeadListener(SocketIOServer server, Game game) {
        super(server, game);
    }

    @Override
    public void onReceivedData(SocketIOClient client, Player data, AckRequest ackRequest) {
        // Broadcast updated players to all clients.
        getGame().getPlayerTeam(client).getMembers().put(client.getSessionId(), data.updateForDeath());
        getServer().getBroadcastOperations().sendEvent(EVENT_DEAD, data);
    }
}
