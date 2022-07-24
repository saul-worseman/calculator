package network;

import calculator.Calculator;
import calculator.CalculatorBasicImpl;
import logger.LoggerAdapater;
import messages.MidMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer implements Server{

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
            //make sure it is the same logger
            LoggerAdapater loggerAdapater = LoggerAdapater.getLoggerAdapter();
            try {
                PrintWriter out = new PrintWriter(
                        clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    loggerAdapater.log("Request " + inputLine + " from " + clientSocket);
                    MidMessage midMessage = Server.parseRequest(inputLine);
                    loggerAdapater.log("OperatorKind: " + midMessage.getOperatorKind());
                    Calculator calculator = new CalculatorBasicImpl();
                    BigInteger res = calculator.calculate(midMessage);
                    loggerAdapater.log(inputLine + " job finished with answer " + res);
                    out.println(res.toString());
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
