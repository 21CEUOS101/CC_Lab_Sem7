package Lab01;

import java.util.Scanner;
import java.util.Stack;

public class InfixToPostfix {

    // Just trying to make it through recursion 
    // Not Main code

    private static String rec(String query, Stack<Character> stack) {
        if (query.length() == 0) {
            // Pop all remaining operators in the stack
            StringBuilder result = new StringBuilder();
            while (!stack.isEmpty()) {
                result.append(stack.pop());
            }
            return result.toString();
        }

        char ch = query.charAt(0);
        if (Character.isLetterOrDigit(ch)) {
            // Directly add operand to result
            return ch + rec(query.substring(1), stack);
        } else if (ch == '(') {
            stack.push(ch);
            return rec(query.substring(1), stack);
        } else if (ch == ')') {
            // Pop from stack until '(' is found
            StringBuilder result = new StringBuilder();
            while (!stack.isEmpty() && stack.peek() != '(') {
                result.append(stack.pop());
            }
            if (!stack.isEmpty() && stack.peek() == '(') {
                stack.pop(); // remove '('
            }
            return result.toString() + rec(query.substring(1), stack);
        } else {
            // Operator
            StringBuilder result = new StringBuilder();
            while (!stack.isEmpty() && precedence(ch) <= precedence(stack.peek())) {
                if(stack.peek() != '(')
                {
                    result.append(stack.pop());
                }
                else
                {
                    stack.pop();
                }
            }
            stack.push(ch);
            return result.toString() + rec(query.substring(1), stack);
        }
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        String query = sc.next();

        Stack<Character> st = new Stack<>();
        // System.out.println(rec(query , st));
        StringBuilder ans = new StringBuilder();
        int i = 0;

        while (i < query.length()) {
            if (query.charAt(i) >= 'a' && query.charAt(i) <= 'z') {
                ans.append(query.charAt(i));
            } else {
                while (!st.isEmpty() && precedence(query.charAt(i)) < precedence(st.peek())) {
                    Character operator = st.pop();

                    ans.append(operator);
                }

                if (query.charAt(i) != '(' && query.charAt(i) != ')') {
                    st.push(query.charAt(i));
                }
            }
            i++;
        }
        while (!st.isEmpty()) {
            ans.append(st.pop());
        }
        System.out.println(ans.toString());

        printQuadripleTable(ans.toString());
    }

    public static int precedence(Character c) {
        return c == '(' ? 9 : c == '^' ? 3 : (c == '*' || c == '/' || c == '%') ? 2 : c == '+' || c == '-' ? 1 : -1;
    }

    public static void printQuadripleTable(String ans) {
        Stack<Character> st = new Stack();

        String result = ans.substring(0, 1);

        System.out.println("Operand1 " + "|" + "Operand2 " + "|" + "Operator |" + "Result");
        for (int i = 1; i < ans.length(); i++) {
            if (ans.charAt(i) >= 'a' && ans.charAt(i) <= 'z') {
                st.push(ans.charAt(i));
            } else {
                String operand1 = result;
                Character operand2 = st.pop();

                result = ans.substring(0, i + 1);
                System.out.println(operand1 + "\t|" + operand2 + "\t|" + ans.charAt(i) + "\t|" + result);

            }
        }
    }
}
