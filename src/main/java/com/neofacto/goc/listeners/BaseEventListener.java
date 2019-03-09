package com.neofacto.goc.listeners;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.neofacto.goc.model.Game;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseEventListener<T> implements DataListener<T> {

    private Game game;
    private SocketIOServer server;

    public BaseEventListener(SocketIOServer server, Game game) {
        this.server = server;
        this.game = game;
    }

    @Override
    public void onData(SocketIOClient socketIOClient, T data, AckRequest ackRequest) {
        onReceivedData(socketIOClient, data, ackRequest);
    }

    public abstract void onReceivedData(SocketIOClient socketIOClient, T data, AckRequest ackRequest);
}
