package Lab01;
import java.util.Scanner;
import java.util.Stack;

public class InfixToPostfix{

    // Just trying to make it through recursion 
    // Not Main code
    public static String rec(String query)
    {
        if(query.length() == 0)
        {
            return "";
        }
        else if(query.length() == 1)
        {
            return String.valueOf(query.charAt(0));
        }

        String operand1 = String.valueOf(query.charAt(0));
        int i = 0;

        if(query.charAt(0) == '(')
        {
            i++;
            StringBuilder sb = new StringBuilder();
            while(i<query.length() && query.charAt(i) != ')')
            {
                sb.append(query.charAt(i));
                i++;
            }
            String brackets = rec(sb.toString());
            operand1 = brackets;
            if(i+1 == query.length())
            {
                return operand1;
            }
        }

        Character operator = query.charAt(i+1);

        String operand2 = rec(query.substring(i+2));

        StringBuilder ans = new StringBuilder();

        ans.append(operand1);

        ans.append(operand2);

        ans.append(operator);

        return ans.toString();
        
    }

    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);

        String query = sc.next();

        // System.out.println(rec(query));

        Stack<Character> st = new Stack<>();
        StringBuilder ans = new StringBuilder();
        int i = 0;

        while(i<query.length())
        {
            if(query.charAt(i) >= 'a' && query.charAt(i) <= 'z')
            {
                ans.append(query.charAt(i));
            }
            else
            {
                while(!st.isEmpty() && precedence(query.charAt(i))  < precedence(st.peek()))
                {
                    Character operator = st.pop();

                    ans.append(operator);
                }

                if(query.charAt(i) != '(' && query.charAt(i) != ')')
                {
                    st.push(query.charAt(i));
                }
            }
            i++;
        }
        while(!st.isEmpty())
        {
            ans.append(st.pop());
        }
        System.out.println(ans.toString());

        printQuadripleTable(ans.toString());
    }

    public static int precedence(Character c)
    {
       return c == '(' ? 9 : c == '^' ? 3 : c == '*' || c == '/' ? 2 : c == '+' || c == '-' ? 1 : -1; 
    }

    public static void printQuadripleTable(String ans)
    {
        Stack<Character> st = new Stack();

        String result = ans.substring(0,1);

        System.out.println("Operand1 " + "|" + "Operand2 " + "|" + "Operator |" + "Result");
        for(int i=1;i<ans.length();i++)
        {
            if(ans.charAt(i) >= 'a' && ans.charAt(i) <= 'z')
            {
                st.push(ans.charAt(i));
            }
            else
            {
                String operand1 = result;
                Character operand2 = st.pop();

                result = ans.substring(0,i+1);
                System.out.println(operand1 + "\t|" + operand2 + "\t|" + ans.charAt(i) + "\t|" + result);

            }
        }
    }
}