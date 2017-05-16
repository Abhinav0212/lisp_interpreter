# lisp_interpreter

Running the program
===================
The instructions for running the program is given below:

java –Xss32m -cp ./classes interpreter.myInterpreter < [Input_File_Name] > [Output_File_Name]

Details of the interpreter
==========================
We start with a lexical analyzer/scanner.

The input to the scanner is a sequence of ASCII characters consisting of upper-case letters: A, B, ..., Z, digits: 0, 1, ..., 9, parentheses: ( ) , and ASCII white spaces. The ASCII characters in the input are used to form five kinds of tokens: Atom, OpenParenthesis, ClosingParenthesis, ERROR, and EOF.
