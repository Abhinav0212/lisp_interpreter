// A separate class for scanning the input
package interpreter;
import java.io.*;
class myScanner
{
  String[] getNextToken() throws IOException
  {
    int ch;
    int startChar = 0; // using startChar to keep track of whether the current character is the first (startChar = 0), part of a numeric atom (1), part of a literal atom(2)
    StringBuilder tokenVal= new StringBuilder(); // For building the literal and numeric atoms
    String[] scannerResult = new String[4]; // 0 - Type of token, 1 - value of token, 2 - type of atom, 3 - reason for the end of atom
    while((ch=System.in.read())!=-1)
    {
      // If current character is an open parentheses
      if(ch==40)
      {
        if(startChar==0)
        {
          scannerResult[0] = "OPEN PARENTHESES";
          return scannerResult;
        }
        else
        {
          scannerResult[0] = "ATOM";
          scannerResult[1] = tokenVal.toString();
          scannerResult[2] = Integer.toString(startChar);
          scannerResult[3] = "OPEN PARENTHESES";
          return scannerResult;
        }
      }
      // If current character is a closing parentheses
      else if(ch==41)
      {
        if(startChar==0)
        {
          scannerResult[0] = "CLOSING PARENTHESES";
          return scannerResult;
        }
        else
        {
          scannerResult[0] = "ATOM";
          scannerResult[1] = tokenVal.toString();
          scannerResult[2] = Integer.toString(startChar);
          scannerResult[3] = "CLOSING PARENTHESES";
          return scannerResult;
        }
      }
      // If current character is an uppercase letter
      else if(ch>=65&&ch<=91)
      {
        if(startChar==0)
        {
          startChar = 2;
        }
        else if(startChar==1)
        {
          tokenVal.append((char)ch);
          scannerResult[0] = "ERROR";
          scannerResult[1] = tokenVal.toString();
          return scannerResult;
        }
        tokenVal.append((char)ch);
      }
      // If current character is a numeral
      else if(ch>=48&&ch<=57)
      {
        if(startChar==0)
        {
          startChar = 1;
        }
        tokenVal.append((char)ch);
      }
      // If current character is a space/new line
      else if(ch==32||ch==13||ch==10)
      {
        if(startChar!=0)
        {
          scannerResult[0] = "ATOM";
          scannerResult[1] = tokenVal.toString();
          scannerResult[2] = Integer.toString(startChar);
          scannerResult[3] = "SPACE";
          return scannerResult;
        }
      }
			else
			{
				tokenVal.append((char)ch);
				scannerResult[0] = "ERROR";
				scannerResult[1] = tokenVal.toString();
				return scannerResult;
			}
    }
    scannerResult[0] = "EOF";
    return scannerResult;
  }
}
