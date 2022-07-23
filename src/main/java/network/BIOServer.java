package network;

import messages.MidMessage;
import messages.Request;
import messages.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer implements Server{

    @Override
    public Response packResult(BigInteger res) {
        return null;
    }

    @Override
    public MidMessage parseRequest(Request request) {
        return null;
    }

    @Override
    public BigInteger processMidMessage(MidMessage midMessage) {
        return null;
    }

    @Override
    public void start(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true)
            new EchoClientHandler(serverSocket.accept()).start();
    }

    private static class EchoClientHandler extends Thread {
        private final Socket clientSocket;

        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            System.out.println(clientSocket);
            try {
                PrintWriter out = new PrintWriter(
                        clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                    out.println("wired");
                    out.flush();
                }
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
