package com.neofacto.goc;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.neofacto.goc.listeners.AttackListener;
import com.neofacto.goc.listeners.JoinGameListener;
import com.neofacto.goc.listeners.PlayerDamagedListener;
import com.neofacto.goc.listeners.PlayerDeadListener;
import com.neofacto.goc.listeners.PositionListener;
import com.neofacto.goc.model.Attack;
import com.neofacto.goc.model.Damage;
import com.neofacto.goc.model.Game;
import com.neofacto.goc.model.Player;
import com.neofacto.goc.model.Position;
import com.neofacto.goc.model.TeamSubscription;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "goc")
public class GameServer {

    public final static String EVENT_ERROR = "error";

    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.setOrigin(args[0]);  // set origin as 1st app argument
        if (args.length > 1 && args[1] != null && !args[1].isEmpty()) {
            config.setPort(Integer.valueOf(args[1]));  // set port as 2nd app argument
        } else {
            config.setPort(9092);
        }
        final SocketIOServer server = new SocketIOServer(config);

        // Init a new game.
        Game game = Game.instantiate();

        // Event listeners.
        server.addEventListener(JoinGameListener.EVENT_JOIN_GAME, TeamSubscription.class, new JoinGameListener(server, game));
        server.addEventListener(PositionListener.EVENT_POSITION, Position.class, new PositionListener(server, game));
        server.addEventListener(AttackListener.EVENT_ATTACK, Attack.class, new AttackListener(server, game));
        server.addEventListener(PlayerDamagedListener.EVENT_DAMAGED, Damage.class, new PlayerDamagedListener(server, game));
        server.addEventListener(PlayerDeadListener.EVENT_DEAD, Player.class, new PlayerDeadListener(server, game));
        server.addDisconnectListener(client -> server.getBroadcastOperations().sendEvent(PlayerDeadListener.EVENT_DEAD,
                game.getPlayerTeam(client).getMembers().get(client.getSessionId()).updateForDeath()));

        // Server start.
        server.start();
    }

}
