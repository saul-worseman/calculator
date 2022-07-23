package network;

import messages.MidMessage;
import messages.Request;
import messages.Response;

import java.io.IOException;
import java.math.BigInteger;

public interface Server {
    Response packResult(BigInteger res);

    MidMessage parseRequest(Request request);

    BigInteger processMidMessage(MidMessage midMessage);

    void start(int port) throws IOException;
}
