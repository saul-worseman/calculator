package calculator;

import messages.MidMessage;

import java.math.BigInteger;

public interface Calculator {
    BigInteger calculate(MidMessage midMessage);
}
