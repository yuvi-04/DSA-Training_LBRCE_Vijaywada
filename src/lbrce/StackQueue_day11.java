package lbrce;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Stack;

public class StackQueue_day11 {
    //Q1: Valid Parenthesis
    //leetcode 20
    public static boolean isValidParenthesis(String s) {
        Stack<Character> stack = new Stack<>();

        for(int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if(ch == '(' || ch == '{' || ch == '[')
                stack.push(ch);
            else {
                if(stack.isEmpty())
                    return false;
                char top = stack.peek();
                if((ch == ')' && top == '(') ||
                   (ch == '}' && top == '{') ||
                   (ch == ']' && top == '[')) {
                    stack.pop();
                } else {
                    return false;
                } 
            }
        }
        return stack.isEmpty();
    }

    //Q2: evaluate Postfix
    //leetcode 150
    public static int evalPostfix(String[] tokens) {
        Stack<Integer> stack = new Stack<>();

        for (String token : tokens) {
            if (isOperator(token)) {
                int val2 = stack.pop();
                int val1 = stack.pop();

                switch (token) {
                    case "+": stack.push(val1+val2); break;
                    case "-": stack.push(val1-val2); break;
                    case "*": stack.push(val1*val2); break;
                    case "/": stack.push(val1/val2); break;
                }
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }
    private static boolean isOperator(String token) {
        return (token.equals("+") ||
            token.equals("-") ||
            token.equals("*") ||
            token.equals("/"));
    }

     //Q3: Infix to Postfix
     //leetcode 227
     public static String[] infixToPostfix(String[] tokens) {
        List<String> result = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        for (String token : tokens) {
            token = token.trim();
            if(token.isEmpty())
                continue;

            //Step1: if it is not operator or brackets
            if(!isOperatorOrBracket(token))
                result.add(token);
            //Step2
            else if(token.equals("("))
                stack.push(token);
            //Step3
            else if(token.equals(")")) {
                while (!stack.isEmpty() &&
                !stack.peek().equals("(")) {
                    result.add(stack.pop());
                }
                if(!stack.isEmpty())
                    stack.pop(); // removing the '('
                else
                    return new String[]{"INVALID EXPRESSION: PARENTHESIS MISMATCH"};
            }
            //Step4: Operator encountered
            else {
                while (!stack.isEmpty() &&
                    (precedence(token) < precedence(stack.peek()) ||
                    (precedence(token) == precedence(stack.peek()) &&
                    !token.equals("^")))
                ) {
                    result.add(stack.pop());
                }
                stack.push(token);
            }
        }
        //clean remaining stack and add to answer
        while (!stack.isEmpty()) {
            if(stack.peek().equals("("))
                return new String[]{"INVALID EXPRESSION"};
            result.add(stack.pop());
        }
        return result.toArray(new String[0]);
     }
      private static boolean isOperatorOrBracket(String token) {
        return (token.equals("+") ||
            token.equals("-") ||
            token.equals("*") ||
            token.equals("/") ||
            token.equals("^") ||
            token.equals("(") ||
            token.equals(")")
        );
    }
    private static int precedence(String token) {
        if (token.length() != 1) return -1;
        char ch = token.charAt(0);
        switch (ch) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }

    //Q4: Find Next Greater Element to right using monotonic stack
    //leetcode 503, 739
    public static int[] findNextGreater(int[] arr) {
        //edge case
        if(arr == null || arr.length == 0)
            throw new IllegalArgumentException("CHECK THE ARRAY");
        
        int n = arr.length;
        int[] result = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = n-1; i >= 0; i--) {
            //maintain monotonic decresing stack
            while(!stack.isEmpty() &&
                stack.peek() <= arr[i])
                stack.pop();
            //if stack is empty, no greater element exits
            if(stack.isEmpty()) result[i] = -1;
            else result[i] = stack.peek();

            //push the current element for further evaluation
            stack.push(arr[i]);
        }
        return result;
    }

    //Q5:  Next Smaller Element to the left
    //leetcode 84
    public static int[] findNextSmaller(int[] arr) {
        //edge case
        if(arr == null || arr.length == 0)
            throw new IllegalArgumentException("CHECK THE ARRAY");
        
        int n = arr.length;
        int[] result = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            //maintain monotonic increasing stack
            while(!stack.isEmpty() &&
                stack.peek() >= arr[i])
                stack.pop();
            //if stack is empty, no snaller element exits
            if(stack.isEmpty()) result[i] = -1;
            else result[i] = stack.peek();

            //push the current element for further evaluation
            stack.push(arr[i]);
        }
        return result;
    }

    //Q6: Calculate Stock Span
    //leetcode 901: it is a stream based variation
    public static int[] stockSpan(int[] prices) {
        //edge case
        if(prices == null || prices.length == 0)
            throw new IllegalArgumentException("CHECK THE ARRAY");
        
        int n = prices.length;
        int[] span = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        for(int i = 0; i<n; i++) {
            while (!stack.isEmpty() &&
                prices[stack.peek()] <= prices[i]) {
                    stack.pop();
            }
            if(stack.isEmpty()) span[i] = i+1;
            else span[i] = i-stack.peek();

            stack.push(i);
        }
        return span;
    }

    //Q7:  histogram of bar heights,  find the area of the largest rectangle
    //leetcode 84
    public static int largestRectangleArea(int[] heights) {
        if(heights == null || heights.length == 0)
            throw new IllegalArgumentException("CHECK THE ARRAY");

        int n = heights.length;
        int maxArea = 0;
        Deque<Integer> stack = new ArrayDeque<>();

        for(int i = 0; i<n; i++) {
            int currentHeight = (i == n) ? 0 : heights[i];

            //Maintain montonic increasing stack of heights
            while (!stack.isEmpty() &&
                currentHeight < heights[stack.peek()]) {
                    int height = heights[stack.pop()];
                    //if stack is empty, then popped value was absolute smallest
                    int width = stack.isEmpty() ? i : i-stack.peek()-1;
                    maxArea = Math.max(maxArea, height*width);
            }
            stack.push(i);
        }
        return maxArea;
    }
    public static void main(String[] args) {
        //Q1
        System.out.println(isValidParenthesis("([]){}"));

        //Q2
        String[] expression = {"2", "1", "+", "3", "*"};
        System.out.println(evalPostfix(expression));

        //Q3
        String[] expression1 = {"(", "2", "+", "1", ")", "*", "3"};
        System.out.println(Arrays.toString(
            infixToPostfix(expression1)));

        //Q4
        System.out.println(Arrays.toString(
            findNextGreater(new int[]{5, 5, 3, 8})));

        //Q5
        System.out.println(Arrays.toString(
            findNextSmaller(new int[]{2, 1, 5, 6, 2, 3})));
        
        //Q6
        System.out.println(Arrays.toString(
            stockSpan(new int[]{100,80,60,70,60,75,85})));
        
        //Q7:
        System.out.println(largestRectangleArea(
            new int[]{2,1,5,6,2,3}));
    }
}
