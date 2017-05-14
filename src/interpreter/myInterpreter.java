package interpreter;
import java.io.*;
import java.util.ArrayList;

public class myInterpreter
{
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
		evalFunction LispInterpreter = new evalFunction();
		Scanner.init();
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
					// Parser.prettyPrintDot(root);
					// System.out.println();
					// Parser.prettyPrintList(root,true);
					// System.out.println();
				}
				Node cal = LispInterpreter.eval(root);
				if(cal==null)
				{
					break;
				}
				else
				{
					// Parser.prettyPrintDot(cal);
					// System.out.println();
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
