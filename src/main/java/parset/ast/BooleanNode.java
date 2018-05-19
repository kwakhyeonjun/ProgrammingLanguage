package parset.ast;

public class BooleanNode implements ValueNode {
    public boolean value;

    @Override
    public String toString(){
        return value ? "#T" : "#F";
    }

    public static BooleanNode FALSE_NODE = new BooleanNode(false);
    public static BooleanNode TRUE_NODE = new BooleanNode(true);

    private BooleanNode(boolean b){
        value = b;
    }
}
