package network;


import messages.Request;

import java.io.*;
import java.net.Socket;
import java.util.regex.Pattern;

public class BIOClient implements Client {
    @Override
    public boolean checkPattern(String input) {
        //more pattern can be added here to filter
        String pattern = "[0([1-9]+\\d*)][+\\-*/][0([1-9]+\\d*)]";
        if (!Pattern.matches(pattern, input)) {
            System.out.println("check your input, it should be like '1+2' without a blank between");
            return false;
        } else
            return true;
    }

    @Override
    public Request packRequest(String input) {
        return new Request(input);
    }

    @Override
    public void sendRequest(Request request) {
        try {
            Socket serverSocket = new Socket("127.0.0.1", 18848);
            OutputStream outToServer = serverSocket.getOutputStream();
            PrintWriter out = new PrintWriter(outToServer);
            out.println(request.getRequestBody());
            out.flush();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(serverSocket.getInputStream()));
            System.out.println("The result is " + in.readLine());
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Client client = new BIOClient();
        boolean res;
        res = client.checkPattern("0+1");
        System.out.println(res);
        res = client.checkPattern("1+0");
        System.out.println(res);
        res = client.checkPattern("1+01");
        System.out.println(res);
        res = client.checkPattern("01+1");
        System.out.println(res);
        res = client.checkPattern("a+b");
        System.out.println(res);
        res = client.checkPattern("1+2");
        System.out.println(res);
        res = client.checkPattern("2*3");
        System.out.println(res);
        res = client.checkPattern("3-4");
        System.out.println(res);
        res = client.checkPattern("4/5");
        System.out.println(res);
        res = client.checkPattern("1 + 2");
        System.out.println(res);
        res = client.checkPattern("111+222");
        System.out.println(res);
    }
}
