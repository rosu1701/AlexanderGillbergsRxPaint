package net;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import io.reactivex.Observable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import net.requests.ClearRequest;
import net.requests.NetRequest;
import net.requests.ShapeRequest;
import net.requests.ShutDownRequest;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NPServer {

    public static final int TCP_PORT = 7171;
    public static final int UDP_PORT = 7172;
    public static String IP_ADDRESS = "127.0.0.1";

    private Server server;
    private List<ShapeRequest> shapeRequests;

    public boolean startServer(){
        try {
            server = new Server(100000, 100000);
            SharedNet.registerServer(server);
            server.start();
            server.bind(TCP_PORT, UDP_PORT);
            shapeRequests = new ArrayList<>();
            System.out.println("Server started!");


        } catch (IOException e) {
            e.printStackTrace();
            server = null;
            return false;
        }

        return true;
    }

    public void listen(){

        connectRequest().subscribeOn(JavaFxScheduler.platform()).subscribe(connection -> {
            for (ShapeRequest shape : shapeRequests){
                server.sendToTCP(connection.getID(), shape);
            }
        });

        receiveRequest().subscribeOn(JavaFxScheduler.platform()).subscribe(server::sendToAllTCP);
    }

    public void closeServer(){
        server.sendToAllTCP(new ShutDownRequest());
        server.stop();
        server = null;
    }

    public boolean isRunning(){
        return this.server != null;
    }

    private Observable<NetRequest> receiveRequest(){
        return Observable.create(e -> server.addListener(new Listener(){
            @Override
            public void received(Connection connection, Object object) {
                if(object instanceof NetRequest){
                    if (object instanceof ShapeRequest){
                        shapeRequests.add((ShapeRequest) object);
                    }
                    if(object instanceof ClearRequest){
                        shapeRequests.clear();
                    }

                    e.onNext((NetRequest) object);
                }


            }
        }));
    }

    private Observable<Connection> connectRequest(){
        return Observable.create(e -> server.addListener(new Listener(){
            @Override
            public void connected(Connection connection) {
                e.onNext(connection);
            }
        }));
    }

}
