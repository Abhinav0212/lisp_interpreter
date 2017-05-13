package interpreter;
import java.io.*;

public class myParser
{
	String[] scannerResult = new String[3];
	static class Node
	{
		Node left;
    	Node right;
    	String value;
		String type;
    	public Node(String value, String type) {
      		this.value = value;
			this.type = type;
    	}
  	}
	public static void main(String[] args) throws IOException
	{
		myParser Parser = new myParser();
		Parser.start();
	}
	void start() throws IOException
	{
		myScanner Test = new myScanner();
		Test.init();
		scannerResult = Test.getCurrentToken();
		if(!scannerResult[0].equals("EOF")){
			while(!scannerResult[0].equals("EOF"))
			{
				Node root = parse(Test);
				if(root==null)
				{
					break;
				}
				else
				{
					prettyPrint(root);
					System.out.println();
				}
			}
		}
		else{
			System.out.println("Error : Empty input string");
		}
	}
	void prettyPrint(Node root)
	{
		if(root.left==null&&root.right==null)
		{
			System.out.print(root.value);
		}
		else
		{
			System.out.print("(");
			prettyPrint(root.left);
			System.out.print(" . ");
			prettyPrint(root.right);
			System.out.print(")");
		}
	}
	Node parse(myScanner Test) throws IOException
	{
		if(scannerResult[0].equals("ATOM"))
		{
			Node current = new Node(scannerResult[1],"LITERAL ATOM");
			if(scannerResult[2].equals("0")){
				current.type = "LITERAL ATOM";
			}
			else{
				current.type = "NUMERIC ATOM";
			}
			Test.moveToNext();
			scannerResult = Test.getCurrentToken();
			return current;
		}
		else if(scannerResult[0].equals("OPEN PARENTHESES"))
		{
			Node head = new Node(" ","SPACE");
			Node temp = head;
			Test.moveToNext();
			scannerResult = Test.getCurrentToken();
			while(!scannerResult[0].equals("CLOSING PARENTHESES"))
			{
				Node leftNode = parse(Test);
				if (leftNode==null)
					return null;
				temp.left = leftNode;
				Node rightNode = new Node(" ","SPACE");
				temp.right = rightNode;
				temp = temp.right;
			}
			temp.value = "NIL";
			temp.type = "NIL";
			Test.moveToNext();
			scannerResult = Test.getCurrentToken();
			return head;
		}
		else if(scannerResult[0].equals("ERROR"))
		{
			System.out.println("ERROR: Invalid token " + scannerResult[1]);
			return null;
		}
		else if(scannerResult[0].equals("CLOSING PARENTHESES"))
		{
			System.out.println("ERROR: CLOSING PARENTHESES without a matching OPEN PARENTHESES");
			return null;
		}
		else
		{
			System.out.println("ERROR: OPEN PARENTHESES without a matching CLOSING PARENTHESES");
			return null;
		}
	}
}
