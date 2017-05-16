/**
	A parser class to build an internal binary tree representation of the input program.
	Input :
		The input language for the parser is defined by the following context-free grammar:
			<Start> ::= <Expr> <Start> | <Expr> eof
			<Expr> ::= atom | ( <List> )
			<List> ::= <Expr> <List> | ε
		Here <Start>, <Expr> and <List> are non-terminals. There are four terminals: atom ( ) eof corresponding to the tokens defined in Scanner class.

	Internal Representation:
	    Our implementation builds a binary tree representation that is traditionally used to represent values and programs in Lisp. In these binary trees, leaf nodes (i.e., nodes with no children) are atoms. Each inner node has exactly two children – a left child and a right child.

	    Each string derived from non-terminal <Expr> is either an atom or a list (E1 E2 ... En) for n≥0. If the expression is an atom, it is represented by a binary tree containing one node (i.e., a node for that atom). If the expression is a list, it is represented by a binary tree containing n inner nodes I1 I2 ... In such that
	    • The root of the tree is I1
	    • The left child of Ik is the root of the binary tree for Ek for 1≤k≤n
	    • The right child of Ik is Ik+1 for 1≤k≤n-1
	    • The right child of In is a leaf node corresponding to the special literal atom NIL
	    In the case when n=0 (that is, we have an empty list), the binary tree contains one leaf node corresponding to the special literal atom NIL.
**/
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
