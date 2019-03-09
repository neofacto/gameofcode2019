package com.neofacto.goc.listeners;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.neofacto.goc.model.Attack;
import com.neofacto.goc.model.Game;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "goc")
public class AttackListener extends BaseEventListener<Attack> {

    public static final String EVENT_ATTACK = "attackEvent";

    public AttackListener(SocketIOServer server, Game game) {
        super(server, game);
    }

    @Override
    public void onReceivedData(SocketIOClient client, Attack data, AckRequest ackRequest) {
        // Check ability to attack.
        if (data.getPlayer().canAttack(data.isUltimate())) {
            // Broadcast attack to all clients.
            getServer().getBroadcastOperations().sendEvent(EVENT_ATTACK, data);
        } else {
            log.debug("{} cannot attack now!", data.getPlayer().getCharacter().name());
        }
    }
}
