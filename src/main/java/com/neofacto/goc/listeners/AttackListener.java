package com.neofacto.goc.listeners;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.neofacto.goc.model.Attack;

public class AttackListener extends BaseEventListener<Attack> {

    public static final String EVENT_ATTACK = "attackEvent";

    public AttackListener(SocketIOServer server) {
        super(server);
    }

    @Override
    public void onReceivedData(SocketIOClient client, Attack data, AckRequest ackRequest) {
        // Broadcast messages to all clients.
        getServer().getBroadcastOperations().sendEvent(EVENT_ATTACK, data);
    }
}
