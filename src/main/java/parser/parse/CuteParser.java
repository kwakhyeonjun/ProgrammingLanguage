package parser.parse;

import lexer.Scanner;
import lexer.Token;
import lexer.TokenType;
import parset.ast.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;

public class CuteParser {
    private Iterator<Token> tokens;
    private static Node END_OF_LIST = new Node(){};//새로 추가됨


    public CuteParser(File file){
        try {
            tokens = Scanner.scan(file);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
    private Token getNextToken(){
        if(!tokens.hasNext())
            return null;
        return tokens.next();
    }
    public Node parseExpr(){
        Token t = getNextToken();
        if(t == null){
            System.out.println("No more token");
            return null;
        }
        TokenType tType = t.type();
        String tLexeme = t.lexme();
        switch (tType){
            case ID:
                return new IdNode(tLexeme);
            case INT:
                if(tLexeme == null)
                    System.out.println("???");
                return new IntNode(tLexeme);
            case DIV:
            case EQ:
            case MINUS:
            case GT:
            case PLUS:
            case TIMES:
            case LT:
                BinaryOpNode binaryNode = new BinaryOpNode();
                binaryNode.setValue(tType);
                return binaryNode;
                // FunctionNode 키워드가 FunctionNode에 해당
            case ATOM_Q:
            case CAR:
            case CDR:
            case COND:
            case CONS:
            case DEFINE:
            case EQ_Q:
            case LAMBDA:
            case NOT:
            case NULL_Q:
                //이때 역시 binaryOpNode와 같이 작성한다.
                //타입을 set하여 return함.
                FunctionNode functionNode = new FunctionNode();
                functionNode.setValue(tType);
                return functionNode;
            case FALSE:
               return BooleanNode.FALSE_NODE;
            case TRUE:
                return BooleanNode.TRUE_NODE;
            case APOSTROPHE:
                return new QuoteNode(parseExpr());
            case QUOTE:
                return new QuoteNode(parseExpr());
            case L_PAREN:
                return parseExprList();
            case R_PAREN:
                return END_OF_LIST;
            default:
                System.out.println("Parsing Error!");
                return null;
        }
    }
    private ListNode parseExprList(){
        Node head = parseExpr();

        if(head == null){
            return null;
        }
        if(head == END_OF_LIST){
            return ListNode.ENDLIST;
        }

        ListNode tail = parseExprList();
        if(tail == null){
            return null;
        }
        return ListNode.cons(head, tail);
    }
}
