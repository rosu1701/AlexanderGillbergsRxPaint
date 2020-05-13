package net;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import io.reactivex.Observable;
import net.requests.NetRequest;

import java.io.IOException;

public class NPClient {

    private Client client = new Client(100000, 100000);

    public boolean startClient(){

        try{
            client.start();
            SharedNet.registerClient(client);
            client.connect(5000, NPServer.IP_ADDRESS, NPServer.TCP_PORT, NPServer.UDP_PORT);
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Observable<NetRequest> listen(){
        return Observable.create(subscriber -> {
           client.addListener(new Listener(){
               public void received(Connection connection, Object object) {
                   if (object instanceof NetRequest){
                       subscriber.onNext((NetRequest) object);
                   }
               }
           });
        });
    }


    public void disconnect(){
        client.stop();
    }

    public void send(Object object){
        client.sendTCP(object);
    }


}
