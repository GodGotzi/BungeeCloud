package at.gotzi.bungeecloud.server.connection;

import at.gotzi.bungeecloud.BungeeCloud;
import at.gotzi.bungeecloud.server.api.InternServerBuilder;

import java.io.*;

public class InternServerHandler {
    private final BungeeCloud bungeeCloud;

    private InternServerSocket internServerSocket;

    public InternServerHandler(BungeeCloud bungeeCloud) {
        this.bungeeCloud = bungeeCloud;
        try {
            this.internServerSocket = new InternServerBuilder(67777, bungeeCloud).build();
        } catch (IOException e) {
            bungeeCloud.getGotziLogger().warning("Couldn't create InternServerSocket");
            bungeeCloud.getErrorHandler().registerError(getClass(), e);
        }
    }

    public byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    public Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    public InternServerSocket getInternServerSocket() {
        return internServerSocket;
    }

    public BungeeCloud getBungeeCloud() {
        return bungeeCloud;
    }

}
