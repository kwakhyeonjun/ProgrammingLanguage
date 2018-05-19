package parset.ast;


import lexer.TokenType;

import java.util.HashMap;
import java.util.Map;

/**
 * function에 해당하는 문자열(ID)에 대해 각각의 값을 지정해주기 위함.
 * fromTokenType이라는 hashmap을 사용하여 각각의 function과 type을 지정해준다.
 * 이후에 setValue사용 예정
 */
public class FunctionNode implements Node {
    enum FunctionType {
        //각각 enum으로 지정한다.

        ATOM_Q  {   TokenType tokenType() {return TokenType.ATOM_Q;}},
        CAR     {   TokenType tokenType() {return TokenType.CAR;}   },
        CDR     {   TokenType tokenType() {return TokenType.CDR;}   },
        COND    {   TokenType tokenType() {return TokenType.COND;}  },
        CONS    {   TokenType tokenType() {return TokenType.CONS;}  },
        DEFINE  {   TokenType tokenType() {return TokenType.DEFINE;}},
        EQ_Q    {   TokenType tokenType() {return TokenType.EQ_Q;}  },
        LAMDA   {   TokenType tokenType() {return TokenType.LAMBDA;}},
        NOT     {   TokenType tokenType() {return TokenType.NOT;}   },
        NULL_Q  {   TokenType tokenType() {return TokenType.NULL_Q;}};

        //hashmap사용
        private static Map<TokenType, FunctionType> fromTokenType = new HashMap<TokenType, FunctionType>();
        static{
            for (FunctionType fType: FunctionType.values()) {
                //각각의 타입에 맞는 함수들을 지정함.
                fromTokenType.put(fType.tokenType(), fType);
            }
        }
        static FunctionType getFunctionType(TokenType tType){
            return fromTokenType.get(tType);
        }
        abstract TokenType tokenType();
    }
    public FunctionType value;

    @Override
    public String toString(){
        //return으로 해당 value의 name을 출력하게 한다.
        return value.name();
    }
    public void setValue(TokenType tType){
        //fType이라는 FunctionType을 생성하여 입력받은 tType과 같은 FunctionType을 찾아 저장한다.
        FunctionType fType = FunctionType.getFunctionType(tType);
        value = fType;
    }
}
