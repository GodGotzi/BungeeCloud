package at.gotzi.bungeecloud.server.api;

import at.gotzi.bungeecloud.BungeeCloud;
import at.gotzi.bungeecloud.server.api.data.MinecraftServerSocket;
import at.gotzi.bungeecloud.server.connection.InternServerSocket;
import at.gotzi.bungeecloud.server.connection.SocketListener;
import net.md_5.bungee.api.config.ServerInfo;

import java.io.IOException;
import java.net.ServerSocket;

public class InternServerBuilder extends ServerSocket {

    private final SocketListener socketListener;

    public InternServerBuilder(int port, BungeeCloud bungeeCloud) throws IOException {
        super(port);
        this.socketListener = new SocketListener(this, bungeeCloud);
    }

    public InternServerSocket build() {

        return new InternServerSocket() {

            @Override
            public MinecraftServerSocket getLastSocket() {
                return InternServerBuilder.this.socketListener.getLastSocket();
            }

            @Override
            public void close() throws IOException {
                InternServerBuilder.this.close();
            }

            @Override
            public void sendBytes(byte[] bytes, ServerInfo serverInfo) {

            }

            @Override
            public byte[] getCurrentInput() {
                return InternServerBuilder.this.socketListener.getLastSocket().getBytes();
            }

            @Override
            public int getPort() {
                return InternServerBuilder.this.getLocalPort();
            }

        };
    }

}
