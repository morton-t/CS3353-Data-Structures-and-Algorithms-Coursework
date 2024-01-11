import java.util.ArrayList;
import java.util.Stack;

public class TreePostfixOperations extends ExpressionTree {
    public ArrayList<String> postfix = new ArrayList<String>();

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Section C ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\\
    //Perform a postorder traversal of the tree to generate the postfix notation
    public void treeToPostfix(Node root) {
        if (root != null) {
            treeToPostfix(root.left);
            
            treeToPostfix(root.right); 

            System.out.print(root.key + " ");
            postfix.add(root.key);
        }
    }

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Section D ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\\
    public double evaluatePostfix() {
        Stack<Double> eval = new Stack<Double>();
        
        System.out.print("\nEvaluating the postfix string: ");

        //If there is only one element, return it
        if (postfix.size() == 1) {
            return Double.parseDouble(postfix.get(0));
        }

        for (int i = 0; i < postfix.size(); ++i) {    
            String val = postfix.get(i);

            //If the node contains a number, push it to the stack
            if (Character.isDigit(val.charAt(0))) {
                eval.push(Double.parseDouble(val));
            }
            //Otherwise, pop the top two elements of the stack and perform the operation stored in val
            else {
                double temp = eval.pop();
                double top = eval.pop();
                double result;

                //Because the stack is of an immutable type Double, the value must be unboxed to perform arithmetic
                //As such, the variable 'top' holds the top of the stack so it may be unboxed 
                //And then the result is pushed back onto the stack
                switch (val) {
                    case "+":
                        result = top + temp;
                        System.out.print("\n\tPerforming: " + top + " " + val + " " + temp);
                        eval.push((Double) result);
                        break;

                    case "-":
                        result = top - temp;
                        System.out.print("\n\tPerforming: " + top + " " + val + " " + temp);
                        eval.push((Double) result);
                        break;

                    case "/":
                        if (temp != 0.0){
                            result = top / temp;
                            System.out.print("\n\tPerforming: " + top + " " + val + " " + temp);
                            eval.push((Double) result);
                        }
                        else {
                            System.out.println("\nDivide by zero error! Returning!");
                            return 0.0;
                        }
                        break;

                    case "*":
                        result = top * temp;
                        System.out.print("\n\tPerforming: " + top + " " + val + " " + temp);
                        eval.push((Double) result);
                        break;

                    case "(": break;
                    case ")": break;

                    default: 
                        System.out.println("Error in stack evaluation for postfix!");
                        return 0.0;
                }
                System.out.printf(" = %.4f", eval.peek());
            }
        }
        //The remaining element is the result of the operation
        return eval.pop();
    }

    public static double evaluateTree(Node node) {
        //Evaluates the expression tree in place through a post-order traversal

        //If there is no node, return 0.0 as an error
        if (node == null) {
            return 0.0;
        }

        //If no left or right child exists, we are at a root node
        if ((node.left == null && node.right == null)) {
            return Double.parseDouble(node.key);
        }

        //Recursively perform postorder traversal to evaluate the tree
        double left = evaluateTree(node.left);
        double right = evaluateTree(node.right);

        //Evaluate the expression based on the operator at the given key
        System.out.print("\n\tPerforming: " + left + " " + node.key + " " + right + " = ");
        switch(node.key) {
            case "+":
                System.out.printf("%.4f", left + right);
                return left + right;
            case "-":
                System.out.printf("%.4f", left - right);
                return left - right;
            case "*":
                System.out.printf("%.4f", left * right);
                return left * right;
            case "/":
                if (right == 0){
                    System.out.print("Divide by zero error!");
                    return 0.0;
                }
                System.out.printf("%.4f", left / right);
                return left / right;
            default:
                System.out.println("Error - invalid evaluation in evaluateTree()!");
                return 0;
        }
    }
}