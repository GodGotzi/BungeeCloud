package at.gotzi.bungeecloud.server.connection;

import at.gotzi.bungeecloud.BungeeCloud;
import at.gotzi.bungeecloud.api.GotziRunnable;
import at.gotzi.bungeecloud.server.api.data.MinecraftServerSocket;
import at.gotzi.bungeecloud.server.process.ProcessCommand;
import net.md_5.bungee.api.config.ServerInfo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketListener {

    private final ServerSocket serverSocket;

    private MinecraftServerSocket last;
    private ProcessCommand lastCommandProcessing;

    private final BungeeCloud bungeeCloud;

    public SocketListener(ServerSocket socket, BungeeCloud bungeeCloud) {
        this.serverSocket = socket;
        this.bungeeCloud = bungeeCloud;
    }

    public void listen() {
        new GotziRunnable() {
            @Override
            public void run() {
                Socket socket;
                try {
                    socket = SocketListener.this.serverSocket.accept();
                    ServerInfo serverInfo = SocketListener.this.bungeeCloud.getServerByPort(socket.getPort());
                    if (serverInfo != null) {
                        byte[] bytes = new byte[20002];
                        socket.getInputStream().read(bytes, 0, bytes.length);
                        socket.close();

                        SocketListener.this.last = (new MinecraftServerSocket(socket,
                                serverInfo, bytes));
                        SocketListener.this.lastCommandProcessing = new ProcessCommand(SocketListener.this.last);
                    }
                } catch (IOException ignored) {
                }
            }
        }.runRepeatingTaskAsync(100);
    }

    public MinecraftServerSocket getLastSocket() {
        return this.last;
    }

    public ProcessCommand getLastCommandProcessing() {
        return lastCommandProcessing;
    }
}
