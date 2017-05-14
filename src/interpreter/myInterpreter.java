package interpreter;
import java.io.*;
import java.util.ArrayList;

public class myInterpreter{
	String[] scannerResult = new String[3];

	public static void main(String[] args) throws IOException
	{
		myInterpreter Interpreter = new myInterpreter();
		Interpreter.start();
	}
	void start() throws IOException
	{
		myScanner Scanner = new myScanner();
		myParser Parser = new myParser();
		evalFunction FunctionEvaluator = new evalFunction();
		Scanner.init();
		FunctionEvaluator.init();
		scannerResult = Scanner.getCurrentToken();
		if(!scannerResult[0].equals("EOF")){
			while(!scannerResult[0].equals("EOF"))
			{
				Node root = Parser.parse(Scanner);
				if(root==null)
				{
					break;
				}
				else
				{
					Parser.assignLength(root);
				}
				Node aList = new Node("NIL","LITERAL ATOM",0);
				Node dList = FunctionEvaluator.getDList();
				Node cal = FunctionEvaluator.eval(root, aList, dList);
				if(cal==null)
				{
					break;
				}
				else
				{
					Parser.prettyPrintList(cal,true);
					System.out.println();
				}
				scannerResult = Scanner.getCurrentToken();
			}
		}
		else{
			System.out.println("Error : Empty input string");
		}
	}
}
