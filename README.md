# lisp_interpreter

Running the program
===================
The instructions for running the program is given below:

java –Xss32m -cp ./classes interpreter.myInterpreter < [Input_File_Name] > [Output_File_Name]

Details of the interpreter
==========================
Scanner
-------
We start with a lexical analyzer/scanner which reads a sequence of characters and converts them into tokens.
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
        • Numeric Atom - combination of one or more numbers..

Parser
------
Next we build a parser on top of the scanner. Internally our parser uses the  technique of recursive-descent parsing to parse the input tokens and builds a binary tree representation of the input program.
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
