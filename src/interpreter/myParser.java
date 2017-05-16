package interpreter;
import java.io.*;

class myParser
{
	String[] scannerResult = new String[3];

	void prettyPrintList(Node root,boolean start)
	{
		if(root.left==null&&root.right==null)
		{
			System.out.print(root.value);
		}
		else
		{
			if((start)){
				System.out.print("(");
			}
			if(root.left.left!=null&&root.left.right!=null)
			{
				System.out.print("(");
			}
			prettyPrintList(root.left,false);
			if(root.right.value.equals("NIL")){
				System.out.print(")");
			}
			else if(root.right.right==null){
				System.out.print(" . ");
				prettyPrintList(root.right,false);
				System.out.print(")");
			}
			else{
				System.out.print(" ");
				prettyPrintList(root.right,false);
			}
		}
	}

	void prettyPrintDot(Node root)
	{
		if(root.left==null&&root.right==null)
		{
			System.out.print(root.value);
		}
		else
		{
			System.out.print("(");
			prettyPrintDot(root.left);
			System.out.print(" . ");
			prettyPrintDot(root.right);
			System.out.print(")");
		}
	}

	int assignLength(Node root){
		if (root.value=="NIL"){
			root.length = 0;
			return 0;
		}
		else{
			// for S-expression which are not a list
			if(root.right==null){
				root.length = 0;
				return 0;
			}
			root.length = assignLength(root.right)+1;
			assignLength(root.left);
			return root.length;
		}
	}

	Node parse(myScanner Scanner) throws IOException
	{
		scannerResult = Scanner.getCurrentToken();
		if(scannerResult[0].equals("ATOM"))
		{
			Node current = new Node(scannerResult[1],"",0);
			if(scannerResult[2].equals("0")){
				current.type = "LITERAL ATOM";
			}
			else{
				current.type = "NUMERIC ATOM";
			}
			Scanner.moveToNext();
			scannerResult = Scanner.getCurrentToken();
			return current;
		}
		else if(scannerResult[0].equals("OPEN PARENTHESES"))
		{
			Node head = new Node(" ","SPACE",0);
			Node temp = head;
			Scanner.moveToNext();
			scannerResult = Scanner.getCurrentToken();
			while(!scannerResult[0].equals("CLOSING PARENTHESES"))
			{
				Node leftNode = parse(Scanner);
				if (leftNode==null)
					return null;
				temp.left = leftNode;
				Node rightNode = new Node(" ","SPACE",0);
				temp.right = rightNode;
				temp = temp.right;
			}
			temp.value = "NIL";
			temp.type = "NIL";
			Scanner.moveToNext();
			scannerResult = Scanner.getCurrentToken();
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
