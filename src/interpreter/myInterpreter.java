package interpreter;
import java.io.*;
import java.util.ArrayList;

public class myInterpreter
{
	public static void main(String[] args) throws IOException
	{
		String[] scannerResult = new String[4];
		scannerResult[0]="Start";
		myScanner Test = new myScanner();
		ArrayList<String> literalList = new ArrayList<String>(); // to store the literal atoms
		int[] tokenCount = {0,0,0,0};	//keep track of the token counts. 0 - Literal atom, 1 - numeric atom, 2 -open parentheses, 3 - closing parentheses
		int numericSum = 0;
		while(!scannerResult[0].equals("EOF"))
		{
			scannerResult = Test.getNextToken();

			if(scannerResult[0].equals("ATOM"))
			{
				if(scannerResult[2].equals("1"))
				{
					numericSum+=Integer.parseInt(scannerResult[1]);
					tokenCount[1]++;
					if(scannerResult[3].equals("OPEN PARENTHESES"))
					{
						tokenCount[2]++;
					}
					else if(scannerResult[3].equals("CLOSING PARENTHESES"))
					{
						tokenCount[3]++;
					}
				}
				else
				{
					literalList.add(scannerResult[1]);
					tokenCount[0]++;
					if(scannerResult[3].equals("OPEN PARENTHESES"))
					{
						tokenCount[2]++;
					}
					else if(scannerResult[3].equals("CLOSING PARENTHESES"))
					{
						tokenCount[3]++;
					}
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
				System.out.println(scannerResult[0] + ": Invalid token " + scannerResult[1]);
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
