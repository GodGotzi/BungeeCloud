package at.gotzi.bungeecloud.server.connection;

import at.gotzi.bungeecloud.server.api.data.MinecraftServerSocket;
import net.md_5.bungee.api.config.ServerInfo;

import java.io.IOException;

public interface InternServerSocket {

    byte[] getCurrentInput();

    int getPort();

    MinecraftServerSocket getLastSocket();

    void close() throws IOException;

    void sendBytes(byte[] bytes, ServerInfo serverInfo);

}
