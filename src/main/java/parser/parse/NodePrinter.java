package parser.parse;

import com.sun.org.apache.xpath.internal.operations.Quo;
import jdk.nashorn.internal.runtime.QuotedStringTokenizer;
import org.w3c.dom.NodeList;
import parset.ast.*;

import java.io.PrintStream;
import java.util.List;

public class NodePrinter {
    PrintStream ps;

    public static NodePrinter getPrinter(PrintStream ps){
        return new NodePrinter(ps);
    }
    private NodePrinter(PrintStream ps){
        this.ps = ps;
    }

    private void printNode(ListNode listNode){
        if(listNode == ListNode.EMPTYLIST){
            ps.print("( ) ");
            return;
        }
        if(listNode == ListNode.ENDLIST){
            return;
        }
        ps.print("( ");
        printNode(listNode.car());
        printNode(listNode.cdr());
        ps.print(") ");
    }

    private void printNode(QuoteNode quoteNode){
        if(quoteNode.nodeInstide() == null){
            return;
        }else{
            ps.print("\' ");
            printNode(quoteNode.nodeInstide());
        }
        quoteNode.toString();
    }

    private void printNode(Node node){
        if( node == null){
            return;
        }else if( node instanceof ListNode){
           ListNode listNode = (ListNode)node;
            printNode(listNode);
        }else if(node instanceof QuoteNode){
            QuoteNode quoteNode = (QuoteNode)node;
            printNode(quoteNode);
        }
        else{
            ps.print("["+node+"] ");
        }
    }
    public void prettyPrint(Node node){
        printNode(node);
    }
}
