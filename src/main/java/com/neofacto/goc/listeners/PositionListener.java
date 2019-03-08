package com.neofacto.goc.listeners;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.neofacto.goc.model.Position;

public class PositionListener extends BaseEventListener<Position> {

    public static final String EVENT_POSITION = "positionEvent";

    public PositionListener(SocketIOServer server) {
        super(server);
    }

    @Override
    public void onReceivedData(SocketIOClient client, Position data, AckRequest ackRequest) {
        // Broadcast messages to all clients.
        getServer().getBroadcastOperations().sendEvent(EVENT_POSITION, data);
    }
}
