package com.neofacto.goc.listeners;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.neofacto.goc.model.Game;
import com.neofacto.goc.model.TeamSubscription;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "goc")
public class JoinGameListener extends BaseEventListener<TeamSubscription> {

    public static final String EVENT_JOIN_GAME = "joinGameEvent";

    public JoinGameListener(SocketIOServer server, Game game) {
        super(server, game);
    }

    @Override
    public void onReceivedData(SocketIOClient client, TeamSubscription data, AckRequest ackRequest) {
        getGame().addPlayer(data.getPlayer(), data.getTeamName(), client);
        log.debug("{} joined {}", data.getPlayer().getCharacter().name(), data.getTeamName());
    }
}
