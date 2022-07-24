package messages;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MidMessage {
    OpeartorKind opeartorKind;
    List<BigInteger> operands = new ArrayList<>();
    List<String> operators = new ArrayList<>();

    public OpeartorKind getOperatorKind() {
        return opeartorKind;
    }

    public void setOperatorKind(OpeartorKind opeartorKind) {
        this.opeartorKind = opeartorKind;
    }

    public List<BigInteger> getOperands() {
        return operands;
    }

    public void setOperands(List<BigInteger> operands) {
        this.operands = operands;
    }

    public List<String> getOperators() {
        return operators;
    }

    public void setOperators(List<String> operators) {
        this.operators = operators;
    }
}
