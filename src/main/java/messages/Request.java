package messages;

public class Request {
    String requestBody;

    public Request(String input){
        requestBody = input;
    }

    public String getRequestBody(){
        return requestBody;
    }

}
