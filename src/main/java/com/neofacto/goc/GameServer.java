package com.neofacto.goc;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.neofacto.goc.listeners.AttackListener;
import com.neofacto.goc.listeners.PositionListener;
import com.neofacto.goc.model.Attack;
import com.neofacto.goc.model.Position;

public class GameServer {

    public static void main(String[] args) throws InterruptedException {

        Configuration config = new Configuration();

        config.setPort(9092);
        config.setOrigin("http://127.0.0.1:8887");

        final SocketIOServer server = new SocketIOServer(config);

        server.addEventListener(PositionListener.EVENT_POSITION, Position.class, new PositionListener(server));
        server.addEventListener(AttackListener.EVENT_ATTACK, Attack.class, new AttackListener(server));

        server.start();

        Thread.sleep(Integer.MAX_VALUE);
        server.stop();

    }


}
