package at.gotzi.bungeecloud.server.api.data;

import net.md_5.bungee.api.config.ServerInfo;

import java.net.Socket;

public record MinecraftServerSocket(Socket socket, ServerInfo serverInfo, byte[] bytes) {

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    public Socket getSocket() {
        return socket;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
