package messages;

import java.math.BigInteger;
import java.util.List;

public class MidMessage {
    OpeartorKind opeartorKind;
    List<BigInteger> opearands;
    List<String> operators;

    public OpeartorKind getOpeartorKind() {
        return opeartorKind;
    }

    public void setOpeartorKind(OpeartorKind opeartorKind) {
        this.opeartorKind = opeartorKind;
    }

    public List<BigInteger> getOpearands() {
        return opearands;
    }

    public void setOpearands(List<BigInteger> opearands) {
        this.opearands = opearands;
    }

    public List<String> getOperators() {
        return operators;
    }

    public void setOperators(List<String> operators) {
        this.operators = operators;
    }
}
