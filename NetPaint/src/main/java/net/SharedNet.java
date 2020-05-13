package net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import net.requests.ClearRequest;
import net.requests.ShapeRequest;
import net.requests.ShutDownRequest;


import java.util.ArrayList;


public class SharedNet {

    public static void registerServer(Server server){
        Kryo kryo = server.getKryo();
        kryo.register(ArrayList.class);
        kryo.register(NetPoint.class);
        kryo.register(ShapeRequest.class);
        kryo.register(ClearRequest.class);
        kryo.register(ShutDownRequest.class);

    }

    public static void registerClient(Client client){
        Kryo kryo = client.getKryo();
        kryo.register(ArrayList.class);
        kryo.register(NetPoint.class);
        kryo.register(ShapeRequest.class);
        kryo.register(ClearRequest.class);
        kryo.register(ShutDownRequest.class);
    }
}
