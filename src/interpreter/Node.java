package interpreter;
class Node
{
	Node left;
	Node right;
	String value;
	String type;
	int length;
	public Node(String value, String type, int length) {
		this.value = value;
		this.type = type;
		this.length = length;
	}
}
