/**
	A Scanner class to parse the input and return tokens as output.
	Expected Input 	:
		A sequence of ASCII characters consisting of
		• upper-case letters: A, B, ..., Z
		• digits: 0, 1, ..., 9
		• parentheses: ( )
		• ASCII white spaces: space (ASCII value 32), carriage return (ASCII value 13), and line feed
		(ASCII value 10).

	Valid tokens :
		Atom, OpenParenthesis, ClosingParenthesis, ERROR, and EOF.
		There are two types of atom namely :
			• Literal Atom - combination of letters and numbers beginning with a letter.
			• Numeric Atom - combination of one or more numbers.
**/
package interpreter;
import java.io.*;
class myScanner
{
	private int startChar ;
	private String[] currentToken = new String[3];
	// gets the first token
	void init() throws IOException
	{
		startChar = getNextChar();
		currentToken = getNextToken();
	}
	// returns current token
	String[] getCurrentToken()
	{
		return currentToken;
	}
	// move to next token
	void moveToNext() throws IOException
	{
		currentToken = getNextToken();
	}

	int getNextChar() throws IOException
	{
		return (System.in.read());
	}

	StringBuilder getLiteralAtom(int ch) throws IOException
	{
		if(isErrorChar(ch))
		{
			StringBuilder errorVal= new StringBuilder();
			errorVal.append("!");
			return errorVal.insert(0,(char)ch);
		}
		else if((ch>=65&&ch<=91)||(ch>=48&&ch<=57))
		{
			return getLiteralAtom(getNextChar()).insert(0,(char)ch);
		}
		else
		{
			StringBuilder val= new StringBuilder();
			return val.append((char)ch);
		}
	}

	StringBuilder getNumeralAtom(int ch) throws IOException
	{
		if(isErrorChar(ch))
		{
			StringBuilder errorVal= new StringBuilder();
			errorVal.append("!");
			return errorVal.insert(0,(char)ch);
		}
		else if((ch>=65&&ch<=91))
		{
			StringBuilder errorVal= new StringBuilder();
			errorVal.append("!");
			return errorVal.insert(0,(char)ch);
		}
		else if((ch>=48&&ch<=57))
		{
			return getNumeralAtom(getNextChar()).insert(0,(char)ch);
		}
		else
		{
			StringBuilder val= new StringBuilder();
			return val.append((char)ch);
		}
	}

	boolean isErrorChar(int ch)
	{
		if(!((ch==40)|(ch==41)||(ch>=65&&ch<=91)||(ch>=48&&ch<=57)||(ch==32)||(ch==13)||(ch==10)||(ch==-1)))
		{
			//System.out.println("ERROR: Invalid token " + (char)ch);
			return true;
		}
		return false;
	}

  String[] getNextToken() throws IOException
  {
	int ch = startChar;
	String[] scannerResult = new String[3]; // 0 - Type of token, 1 - value of token, 2 - type of atom, 3 - next start character

    // If current character is an open parentheses
    if(ch==40)
    {
    	scannerResult[0] = "OPEN PARENTHESES";
		startChar = getNextChar();
    	return scannerResult;
    }
    // If current character is a closing parentheses
    else if(ch==41)
    {
      	scannerResult[0] = "CLOSING PARENTHESES";
		startChar = getNextChar();
		return scannerResult;
    }
    // If current character is an uppercase letter
    else if(ch>=65&&ch<=91)
    {
		String result = getLiteralAtom(ch).toString();
		if(result.charAt(result.length()-1)=='!')
		{
			scannerResult[0] = "ERROR";
			scannerResult[1] = result.substring(0,result.length()-1);
			return scannerResult;
		}
		else
		{
			scannerResult[0] = "ATOM";
			scannerResult[1] = result.substring(0,result.length()-1);
			scannerResult[2] = "0";
			startChar = result.charAt(result.length()-1);
			return scannerResult;
		}
    }
    // If current character is a numeral
    else if(ch>=48&&ch<=57)
    {
		String result = getNumeralAtom(ch).toString();
		if(result.charAt(result.length()-1)=='!')
		{
			scannerResult[0] = "ERROR";
			scannerResult[1] = result.substring(0,result.length()-1);
			return scannerResult;
		}
		else
		{
			scannerResult[0] = "ATOM";
			scannerResult[1] = result.substring(0,result.length()-1);
			scannerResult[2] = "1";
			startChar = result.charAt(result.length()-1);
			return scannerResult;
		}
    }
    // If current character is a space/new line
    else if(ch==32||ch==13||ch==10)
    {
		startChar = getNextChar();
		return getNextToken();
    }
	// If current character is an EOF
	else if(ch==-1)
	{
		scannerResult[0] = "EOF";
    	return scannerResult;
	}
	// Any other input character is an ERROR
	else
	{
		scannerResult[0] = "ERROR";
		scannerResult[1] = Character.toString((char)ch);
		return scannerResult;
	}
  }
}
