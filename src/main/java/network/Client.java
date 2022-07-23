package network;

import messages.Request;

public interface Client {


    //check pattern, filter some illegal input
    boolean checkPattern(String input);
    Request packRequest(String input);

    void sendRequest(Request request);

    
}
