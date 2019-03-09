package com.neofacto.goc;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.neofacto.goc.listeners.AttackListener;
import com.neofacto.goc.listeners.JoinGameListener;
import com.neofacto.goc.listeners.PositionListener;
import com.neofacto.goc.model.Attack;
import com.neofacto.goc.model.Game;
import com.neofacto.goc.model.Position;
import com.neofacto.goc.model.TeamSubscription;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "goc")
public class GameServer {

    public final static String EVENT_ERROR = "error";

    public static void main(String[] args) throws InterruptedException {
        Configuration config = new Configuration();
        config.setPort(9092);
        config.setOrigin(args[0]);  // set origin as app argument
        final SocketIOServer server = new SocketIOServer(config);

        // Init a new game.
        Game game = Game.instantiate();

        // Event listeners.
        server.addEventListener(JoinGameListener.EVENT_JOIN_GAME, TeamSubscription.class, new JoinGameListener(server, game));
        server.addEventListener(PositionListener.EVENT_POSITION, Position.class, new PositionListener(server, game));
        server.addEventListener(AttackListener.EVENT_ATTACK, Attack.class, new AttackListener(server, game));

        // Server start.
        server.start();
    }

}
