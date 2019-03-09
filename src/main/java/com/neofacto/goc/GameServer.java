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

public class GameServer {

    public final static String EVENT_ERROR = "error";

    public static void main(String[] args) throws InterruptedException {
        Configuration config = new Configuration();
        config.setPort(9092);
        config.setOrigin("http://127.0.0.1:8887 192.168.201.14:8887");

        final SocketIOServer server = new SocketIOServer(config);

        final Game game = Game.builder().build();
        game.addTeam("team1");
        game.addTeam("team2");

        server.addEventListener(JoinGameListener.EVENT_JOIN_GAME, TeamSubscription.class, new JoinGameListener(server, game));
        server.addEventListener(PositionListener.EVENT_POSITION, Position.class, new PositionListener(server, game));
        server.addEventListener(AttackListener.EVENT_ATTACK, Attack.class, new AttackListener(server, game));

        server.start();

        Thread.sleep(Integer.MAX_VALUE);
        server.stop();

    }


}
