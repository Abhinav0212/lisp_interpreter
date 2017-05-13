package interpreter;
import java.io.*;
import java.util.ArrayList;

public class myInterpreter
{
	public static void main(String[] args) throws IOException
	{
		String[] scannerResult = new String[3];
		scannerResult[0] = "Start";
		myScanner Test = new myScanner();
		ArrayList<String> literalList = new ArrayList<String>(); // store the list of literal atoms
		int[] tokenCount = {0,0,0,0};	//keep track of the token counts. 0 - Literal atom, 1 - numeric atom, 2 -open parentheses, 3 - closing parentheses
		int numericSum = 0;	// store the sum of numeral atoms
		Test.init();

		while(!scannerResult[0].equals("EOF"))
		{
			//System.out.println((char)startChar);
			scannerResult = Test.getNextToken();

			if(scannerResult[0].equals("ATOM"))
			{
				if(scannerResult[2].equals("1"))
				{
					numericSum+=Integer.parseInt(scannerResult[1]);
					tokenCount[1]++;
				}
				else
				{
					literalList.add(scannerResult[1]);
					tokenCount[0]++;
				}
			}
			else if(scannerResult[0].equals("OPEN PARENTHESES"))
			{
				tokenCount[2]++;
			}
			else if(scannerResult[0].equals("CLOSING PARENTHESES"))
			{
				tokenCount[3]++;
			}
			else if(scannerResult[0].equals("ERROR"))
			{
				System.out.println("ERROR: Invalid token " + scannerResult[1]);
				return;
			}
		}
		System.out.print("LITERAL ATOMS: " + tokenCount[0]);
		for(int i=0;i<literalList.size();i++)
		{
			System.out.print(", " + literalList.get(i));
		}
		System.out.println();
		System.out.println("NUMERIC ATOMS: " + tokenCount[1] + ", " + numericSum);
		System.out.println("OPEN PARENTHESES: " + tokenCount[2]);
		System.out.println("CLOSING PARENTHESES: " + tokenCount[3]);
	}
}
