package com.neofacto.goc.listeners;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import lombok.Getter;
import lombok.Setter;

public abstract class BaseEventListener<T> implements DataListener<T> {

    @Getter
    @Setter
    private SocketIOServer server;

    public BaseEventListener(SocketIOServer server) {
        this.server = server;
    }

    @Override
    public void onData(SocketIOClient socketIOClient, T data, AckRequest ackRequest) {
        onReceivedData(socketIOClient, data, ackRequest);
    }

    public abstract void onReceivedData(SocketIOClient socketIOClient, T data, AckRequest ackRequest);
}
