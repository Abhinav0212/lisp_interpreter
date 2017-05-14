package interpreter;
class evalFunction{
	Node eval(Node root){
		if(root.length==0){
			if(root.value.equals("NIL")||root.type.equals("NUMERIC ATOM")||root.value.equals("T")){
				return root;
			}
			else{
				System.out.println("Error : invalid atom " + root.value);
				return null;
			}
		}
		else{
			Node expression = root.left;
			String expression_val = expression.value;
			if(expression_val.equals("PLUS")){
				if(root.length!=3){
					System.out.println("Error : invalid number of parameters for PLUS (expected 2): " + (root.length-1));
					return null;
				}
				else{
					Node s1 = root.right.left;
					Node s2 = root.right.right.left;
					return plus(s1,s2);
				}
			}
			else if(expression_val.equals("MINUS")){
				if(root.length!=3){
					System.out.println("Error : invalid number of parameters for MINUS (expected 2): " + (root.length-1));
					return null;
				}
				else{
					Node s1 = root.right.left;
					Node s2 = root.right.right.left;
					return minus(s1,s2);
				}
			}
			else if(expression_val.equals("TIMES")){
				if(root.length!=3){
					System.out.println("Error : invalid number of parameters for TIMES (expected 2): " + (root.length-1));
					return null;
				}
				else{
					Node s1 = root.right.left;
					Node s2 = root.right.right.left;
					return times(s1,s2);
				}
			}
			else if(expression_val.equals("LESS")){
				if(root.length!=3){
					System.out.println("Error : invalid number of parameters for LESS (expected 2): " + (root.length-1));
					return null;
				}
				else{
					Node s1 = root.right.left;
					Node s2 = root.right.right.left;
					return less(s1,s2);
				}
			}
			else if(expression_val.equals("GREATER")){
				if(root.length!=3){
					System.out.println("Error : invalid number of parameters for GREATER (expected 2): " + (root.length-1));
					return null;
				}
				else{
					Node s1 = root.right.left;
					Node s2 = root.right.right.left;
					return greater(s1,s2);
				}
			}
			else if(expression_val.equals("EQ")){
				if(root.length!=3){
					System.out.println("Error : invalid number of parameters for EQ (expected 2): " + (root.length-1));
					return null;
				}
				else{
					Node s1 = root.right.left;
					Node s2 = root.right.right.left;
					return eq(s1,s2);
				}
			}
			else if(expression_val.equals("ATOM")){
				if(root.length!=2){
					System.out.println("Error : invalid number of parameters for ATOM : (expected 1)" + (root.length-1));
					return null;
				}
				else{
					Node s1 = root.right.left;
					return atom(s1);
				}
			}
			else if(expression_val.equals("NULL")){
				if(root.length!=2){
					System.out.println("Error : invalid number of parameters for NULL (expected 1): " + (root.length-1));
					return null;
				}
				else{
					Node s1 = root.right.left;
					return Null(s1);
				}
			}
			else if(expression_val.equals("INT")){
				if(root.length!=2){
					System.out.println("Error : invalid number of parameters for INT (expected 1): " + (root.length-1));
					return null;
				}
				else{
					Node s1 = root.right.left;
					return Int(s1);
				}
			}
			else if(expression_val.equals("CAR")){
				if(root.length!=2){
					System.out.println("Error : invalid number of parameters for CAR (expected 1): " + (root.length-1));
					return null;
				}
				else{
					Node s1 = root.right.left;
					return car(s1);
				}
			}
			else if(expression_val.equals("CDR")){
				if(root.length!=2){
					System.out.println("Error : invalid number of parameters for CDR (expected 1): " + (root.length-1));
					return null;
				}
				else{
					Node s1 = root.right.left;
					return cdr(s1);
				}
			}
			else if(expression_val.equals("CONS")){
				if(root.length!=3){
					System.out.println("Error : invalid number of parameters for CONS (expected 2): " + (root.length-1));
					return null;
				}
				else{
					Node s1 = root.right.left;
					Node s2 = root.right.right.left;
					return cons(s1,s2);
				}
			}
			else if(expression_val.equals("QUOTE")){
				if(root.length!=2){
					System.out.println("Error : invalid number of parameters for QUOTE (expected 1): " + (root.length-1));
					return null;
				}
				else{
					Node s1 = root.right.left;
					return quote(s1);
				}
			}
			else if(expression_val.equals("COND")){
				Node temp = root.right;
				if(root.length==1){
					System.out.println("Error : No parameters for COND");
					return null;
				}
				while(temp!=null&&!temp.value.equals("NIL")){
					if(temp.left.right==null){
						System.out.println("Error : parameters for COND is not a list ");
						return null;
					}
					else if(temp.left.length!=2){
						System.out.println("Error : invalid length of parameter for COND (expected 2): " + temp.left.length);
						return null;
					}
					temp = temp.right;
				}
				Node params = root.right;
				return cond(params);
			}
			else if(expression_val.equals(" ")){
				System.out.print("Error : invalid function call (value of car(s) is not an atom) : ");
				myParser Parser = new myParser();
				Parser.prettyPrintList(expression,true);
				System.out.println();
				return null;
			}
			else{
				System.out.println("Error : invalid function call (value of car(s)) : " + expression_val);
				return null;
			}
		}
	}
	Node plus(Node s1, Node s2){
		Node s1_eval = eval(s1);
		Node s2_eval = eval(s2);
		if(s1_eval==null||s2_eval==null){
			return null;
		}
		if(s1_eval.length!=0||!s1_eval.type.equals("NUMERIC ATOM")){
			System.out.println("Error : parameter for PLUS is not a numeric atom  : " + s1_eval.value);
			return null;
		}
		else if(s2_eval.length!=0||!s2_eval.type.equals("NUMERIC ATOM")){
			System.out.println("Error : parameter for PLUS is not a numeric atom  : " + s2_eval.value);
			return null;
		}
		int val = Integer.parseInt(s1_eval.value) + Integer.parseInt(s2_eval.value);
		Node result = new Node(""+val,"NUMERIC ATOM",0);
		return result;
	}
	Node minus(Node s1, Node s2){
		Node s1_eval = eval(s1);
		Node s2_eval = eval(s2);
		if(s1_eval==null||s2_eval==null){
			return null;
		}
		if(s1_eval.length!=0||!s1_eval.type.equals("NUMERIC ATOM")){
			System.out.println("Error : parameter for MINUS is not a numeric atom  : " + s1_eval.value);
			return null;
		}
		else if(s2_eval.length!=0||!s2_eval.type.equals("NUMERIC ATOM")){
			System.out.println("Error : parameter for MINUS is not a numeric atom  : " + s2_eval.value);
			return null;
		}
		int val = Integer.parseInt(s1_eval.value) - Integer.parseInt(s2_eval.value);
		Node result = new Node(""+val,"NUMERIC ATOM",0);
		return result;
	}
	Node times(Node s1, Node s2){
		Node s1_eval = eval(s1);
		Node s2_eval = eval(s2);
		if(s1_eval==null||s2_eval==null){
			return null;
		}
		if(s1_eval.length!=0||!s1_eval.type.equals("NUMERIC ATOM")){
			System.out.println("Error : parameter for TIMES is not a numeric atom  : " + s1_eval.value);
			return null;
		}
		else if(s2_eval.length!=0||!s2_eval.type.equals("NUMERIC ATOM")){
			System.out.println("Error : parameter for TIMES is not a numeric atom  : " + s2_eval.value);
			return null;
		}
		int val = Integer.parseInt(s1_eval.value) * Integer.parseInt(s2_eval.value);
		Node result = new Node(""+val,"NUMERIC ATOM",0);
		return result;
	}
	Node less(Node s1, Node s2){
		Node s1_eval = eval(s1);
		Node s2_eval = eval(s2);
		if(s1_eval==null||s2_eval==null){
			return null;
		}
		if(s1_eval.length!=0||!s1_eval.type.equals("NUMERIC ATOM")){
			System.out.println("Error : parameter for LESS is not a numeric atom  : " + s1_eval.value);
			return null;
		}
		else if(s2_eval.length!=0||!s2_eval.type.equals("NUMERIC ATOM")){
			System.out.println("Error : parameter for LESS is not a numeric atom  : " + s2_eval.value);
			return null;
		}
		Node result = new Node("","LITERAL ATOM",0);
		if(Integer.parseInt(s1_eval.value) < Integer.parseInt(s2_eval.value)) {
			result.value = "T";
		}
		else{
			result.value = "NIL";
		}
		return result;
	}
	Node greater(Node s1, Node s2){
		Node s1_eval = eval(s1);
		Node s2_eval = eval(s2);
		if(s1_eval==null||s2_eval==null){
			return null;
		}
		if(s1_eval.length!=0||!s1_eval.type.equals("NUMERIC ATOM")){
			System.out.println("Error : parameter for GREATER is not a numeric atom: " + s1_eval.value);
			return null;
		}
		else if(s2_eval.length!=0||!s2_eval.type.equals("NUMERIC ATOM")){
			System.out.println("Error : parameter for GREATER is not a numeric atom : " + s2_eval.value);
			return null;
		}
		Node result = new Node("","LITERAL ATOM",0);
		if(Integer.parseInt(s1_eval.value) > Integer.parseInt(s2_eval.value)) {
			result.value = "T";
		}
		else{
			result.value = "NIL";
		}
		return result;
	}
	Node eq(Node s1, Node s2){
		Node s1_eval = eval(s1);
		Node s2_eval = eval(s2);
		if(s1_eval==null||s2_eval==null){
			return null;
		}
		if(s1_eval.length!=0){
			System.out.print("Error : parameter for EQ is not an atom : ");
			myParser Parser = new myParser();
			Parser.prettyPrintList(s1_eval,true);
			System.out.println();
			return null;
		}
		else if(s2_eval.length!=0){
			System.out.print("Error : parameter for EQ is not an atom : ");
			myParser Parser = new myParser();
			Parser.prettyPrintList(s2_eval,true);
			System.out.println();
			return null;
		}
		Node result = new Node("","LITERAL ATOM",0);
		if(s1_eval.value.equals(s2_eval.value)) {
			result.value = "T";
		}
		else{
			result.value = "NIL";
		}
		return result;
	}
	Node atom(Node s1){
		Node s1_eval = eval(s1);
		if(s1_eval==null){
			return null;
		}
		Node result = new Node("","LITERAL ATOM",0);
		if(s1_eval.length==0){
			result.value = "T";
		}
		else{
			result.value = "NIL";
		}
		return result;
	}
	Node Null(Node s1){
		Node s1_eval = eval(s1);
		if(s1_eval==null){
			return null;
		}
		Node result = new Node("","LITERAL ATOM",0);
		if(s1_eval.value.equals("NIL")){
			result.value = "T";
		}
		else{
			result.value = "NIL";
		}
		return result;
	}
	Node Int(Node s1){
		Node s1_eval = eval(s1);
		if(s1_eval==null){
			return null;
		}
		Node result = new Node("","LITERAL ATOM",0);
		if(s1_eval.type.equals("NUMERIC ATOM")){
			result.value = "T";
		}
		else{
			result.value = "NIL";
		}
		return result;
	}
	Node car(Node s1){
		Node s1_eval = eval(s1);
		if(s1_eval==null){
			return null;
		}
		else if(s1_eval.length==0){
			System.out.println("Error : parameter for CAR is an atom : " + s1.value);
			return null;
		}
		else{
			return s1_eval.left;
		}
	}
	Node cdr(Node s1){
		Node s1_eval = eval(s1);
		if(s1_eval==null){
			return null;
		}
		else if(s1_eval.length==0){
			System.out.println("Error : parameter for CDR is an atom : " + s1.value);
			return null;
		}
		else{
			return s1_eval.right;
		}
	}
	Node cons(Node s1, Node s2){
		Node s1_eval = eval(s1);
		Node s2_eval = eval(s2);
		if(s1_eval==null||s2_eval==null){
			return null;
		}
		Node result = new Node(" ","SPACE",s2_eval.length+1);
		result.left = s1_eval;
		result.right = s2_eval;
		return result;
	}
	Node quote(Node s1){
		if(s1==null){
			return null;
		}
		return s1;
	}
	Node cond(Node params){
		myParser Parser = new myParser();
		if(params==null){
			System.out.println("Error : No parameters for COND");
		}
		Node temp = params;
		do{

			Node cond1 = temp.left;
			Node e1 = cond1.left;
			Node e1_eval = eval(e1);
			if(e1_eval==null){
				return null;
			}else if(!e1_eval.value.equals("NIL")){
				Node s1 = cond1.right.left;
				Node s1_eval = eval(s1);
				if(s1_eval==null){
					return null;
				}
				else{
					return s1_eval;
				}
			}
			temp = temp.right;
		}while(temp!=null&&!temp.value.equals("NIL"));
		System.out.println("Error : All conditions evaluated to NIL for COND");
		return null;
	}
}
