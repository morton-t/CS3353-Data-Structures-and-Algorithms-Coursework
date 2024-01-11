import java.util.ArrayList;
import java.util.Stack;

public class ExpressionTree {
	Node root;
    public int numNodes = 0;

	public class Node {
		public String key;
		public Node left = null;
		public Node right = null;

        //constructors
		public Node() {
            root = null;
		}
		public Node(String key) {
			this.key = key;
		}
        public Node (String key, Node left) {
            this.key = key;
            this.left = left;
        }
        public Node(String key, Node left, Node right) {
			this.key = key;
            this.left = left;
            this.right = right;
		}
	}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Section B ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\\
    public void constructTree(ArrayList<String> tokens) {
        Stack<Node> nodes = new Stack<Node>();
        Stack<String> symbols = new Stack<String>();
        Node left;
        Node right;

        //Iterate through each element from the given input
        for (int i = 0; i < tokens.size(); ++i) {
            String val = tokens.get(i);

            //If the current element is a number, create a node for it and push it to the stack of nodes
            if (Character.isDigit(val.charAt(0))) {
                nodes.push(new Node(val));
            } 
            //If it is an open parenthesis, push the symbol to the stack
            else if (val.equals("(")) {
                symbols.push(val);
            }
            //If it is a closed parenthesis, build a node from the symbols and nodes on the stack
            else if (val.equals(")")) {
                while (!symbols.peek().equals("(")) {
                    //First node popped from stack is rightmost
                    right = nodes.pop();
                    left = nodes.pop();
                    nodes.push(new Node(symbols.pop(), left, right));
                }
                //Then remove the open parenthesis from the symbols stack
                symbols.pop();
            } 
            //If the symbol is an operator, build nodes using the operator stack until a parenthesis is encountered
            else {
                while (!symbols.isEmpty() && compareSymbols(symbols.peek(), val)) {
                    //First node popped from stack is rightmost
                    right = nodes.pop();
                    left = nodes.pop();
                    nodes.push(new Node(symbols.pop(), left, right));
                }
                //Then push the last seen operator to the stack
                symbols.push(val);
            }
        }

        //If there are still symbols on the symbol stack, build nodes from them
        while (!symbols.isEmpty()) {
            //First node popped from stack is rightmost
            right = nodes.pop();
            left = nodes.pop();
            nodes.push(new Node(symbols.pop(), left, right));  
        }
        //Set the root node
        root = nodes.peek(); 
    }


    //Compares the operator on the symbol stack to the current array element
    public boolean compareSymbols(String top, String val) {
        //If the read symbol is a closed parenthesis and the top is an open, there is no node to be built
        if (top.equals("(") || val.equals(")")) {
            return false;
        }
        //Otherwise, check if the top of the stack is a higher precedence operator
        else if (top.equals("*") || top.equals("/")) {
            return true;
        }
        //Otherwise, check if the read symbol is an operator
        else if (val.equals("+") || val.equals("-")) {
            return true;
        }
        return false;
    }
}
