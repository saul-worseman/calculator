import messages.Request;
import network.BIOClient;
import network.BIOServer;
import network.Client;
import network.Server;

import java.io.*;
import java.lang.ref.Cleaner;
import java.net.Socket;

public class ApplicationMain {
    public static void main(String[] args){
        if(args.length != 1){
            System.out.println("use one parameter, like 'server' or 'client'");
        }
        else{
            String singleArg = args[0];
            if(singleArg.equals("server")){
                Server server = new BIOServer();
                Thread serverThread = new Thread(()->{
                    try {
                        server.start(18848);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                serverThread.start();
            }
            else if(singleArg.equals("client")){
                Client client = new BIOClient();
                Thread clientThread = new Thread(()->{
                   System.out.print("Input your expression, it should like 1+1:");
                   BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                    try {
                        String input = bufferedReader.readLine();
                        if(client.checkPattern(input)){
                            Request request = client.packRequest(input);
                            client.sendRequest(request);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                clientThread.start();
            }
            else{
                System.out.println("wrong args");
            }
        }
    }
}
