package com.neofacto.goc.listeners;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.neofacto.goc.model.Game;
import com.neofacto.goc.model.Position;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "goc")
public class PositionListener extends BaseEventListener<Position> {

    public static final String EVENT_POSITION = "positionEvent";

    public PositionListener(SocketIOServer server, Game game) {
        super(server, game);
    }

    @Override
    public void onReceivedData(SocketIOClient client, Position data, AckRequest ackRequest) {
        log.debug("{}({}): {}, {}", data.getPlayer().getCharacter().name(),
                getGame().getPlayerTeam(client), data.getX(), data.getY());
        // Broadcast new position to all clients.
        getServer().getBroadcastOperations().sendEvent(EVENT_POSITION, data);
    }
}
