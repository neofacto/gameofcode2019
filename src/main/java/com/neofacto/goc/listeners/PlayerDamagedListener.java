package com.neofacto.goc.listeners;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.neofacto.goc.model.Damage;
import com.neofacto.goc.model.Game;
import com.neofacto.goc.model.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j(topic = "goc")
public class PlayerDamagedListener extends BaseEventListener<Damage> {

    public static final String EVENT_DAMAGED = "damageEvent";

    public PlayerDamagedListener(SocketIOServer server, Game game) {
        super(server, game);
    }

    @Override
    public void onReceivedData(SocketIOClient client, Damage data, AckRequest ackRequest) {
        // Broadcast updated players to all clients.
        Player attacker = data.getAttack().getPlayer().updateScore(data.getAttack().isUltimate());
        Player playerDamaged = data.getPlayer().updateDamages(data.getAttack().isUltimate());
        getGame().getPlayerTeam(client).getMembers().put(client.getSessionId(), playerDamaged);
        getGame().getPlayerTeam(attacker.getName()).getMembers().put(UUID.fromString(attacker.getUuid()), attacker);
        getServer().getBroadcastOperations().sendEvent(EVENT_DAMAGED, attacker, playerDamaged);
    }
}
