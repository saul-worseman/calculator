package network;

import calculator.Calculator;
import calculator.CalculatorBasicImpl;
import messages.MidMessage;
import messages.OpeartorKind;

import java.io.IOException;
import java.math.BigInteger;

public interface Server {

    //patterns can be added here to match more complex expression, and more midmessages can be generated.
     static MidMessage parseRequest(String request) {
        MidMessage midMessage = new MidMessage();
        midMessage.setOperatorKind(OpeartorKind.BINOP);
        String opeartors = "[+\\-*/]";
        String[] twoOperands = request.split(opeartors);

        for(var op: twoOperands){
            midMessage.getOperands().add(new BigInteger(op));
        }

        //get operator
        midMessage.getOperators().add(String.valueOf(request.charAt(twoOperands[0].length())));
        return midMessage;
    }

    static BigInteger processMidMessage(MidMessage midMessage) {
        Calculator calculator = new CalculatorBasicImpl();
        return calculator.calculate(midMessage);
    }

    void start(int port) throws IOException;
}
