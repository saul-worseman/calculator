package calculator;

import messages.MidMessage;
import messages.OpeartorKind;

import java.math.BigInteger;

public class CalculatorBasicImpl implements Calculator{
    @Override
    public BigInteger calculate(MidMessage midMessage) {
        if(midMessage.getOperatorKind() == OpeartorKind.BINOP) {
            BigInteger lhs = midMessage.getOperands().get(0);
            BigInteger rhs = midMessage.getOperands().get(1);
            String operator = midMessage.getOperators().get(0);
            switch (operator) {
                case "+":
                    return lhs.add(rhs);
                case "-":
                    return lhs.subtract(rhs);
                case "*":
                    return lhs.multiply(rhs);
                case "/":
                    return lhs.divide(rhs);
                default:
                    return new BigInteger("0");
            }
        }
        else{
            return new BigInteger("0");
        }
    }
}
