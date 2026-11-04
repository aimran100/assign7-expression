package assignment;

import java.util.EmptyStackException;

public class Stack<T> implements StackInterface<T>, ExpressionEvaluatorInterface {

    private Node<T> top;
    private int size;

    // Helper Node class
    private static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public Stack() {
        this.top = null;
        this.size = 0;
    }

    // Pushes an item onto the stack
    @Override
    public void push(T item) {
        Node<T> newNode = new Node<>(item);
        newNode.next = top;
        top = newNode;
        size++;
    }

    // Removes and returns the top item
    @Override
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }

        T data = top.data;
        top = top.next;
        size--;

        return data;
    }

    // Returns the top item without removing it
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }

        return top.data;
    }

    // Checks whether the stack is empty
    @Override
    public boolean isEmpty() {
        return top == null;
    }

    // Returns the number of items in the stack
    @Override
    public int size() {
        return size;
    }

    // Determines operator precedence
    private int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        }

        if (operator == '*' || operator == '/') {
            return 2;
        }

        return 0;
    }

    // Checks whether a character is an operator
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    // Converts an infix expression to postfix
    @Override
    public String infixToPostfix(String infix) {

        Stack<Character> operators = new Stack<>();
        StringBuilder postfix = new StringBuilder();

        int i = 0;

        while (i < infix.length()) {

            char current = infix.charAt(i);

            // Ignore spaces
            if (Character.isWhitespace(current)) {
                i++;
                continue;
            }

            // Add numbers directly to postfix
            if (Character.isDigit(current)) {

                while (i < infix.length() &&
                       Character.isDigit(infix.charAt(i))) {

                    postfix.append(infix.charAt(i));
                    i++;
                }

                postfix.append(" ");
                continue;
            }

            // Handle opening parentheses
            if (current == '(') {
                operators.push(current);
            }

            // Handle closing parentheses
            else if (current == ')') {

                while (!operators.isEmpty() &&
                       operators.peek() != '(') {

                    postfix.append(operators.pop());
                    postfix.append(" ");
                }

                if (operators.isEmpty()) {
                    throw new IllegalArgumentException(
                        "The parenthesis are not matched correctly!!"
                    );
                }

                operators.pop();
            }

            // Handle operators
            else if (isOperator(current)) {

                while (!operators.isEmpty() &&
                       operators.peek() != '(' &&
                       precedence(operators.peek()) >= precedence(current)) {

                    postfix.append(operators.pop());
                    postfix.append(" ");
                }

                operators.push(current);
            }

            else {
                throw new IllegalArgumentException(
                    "This character is not valid: " + current
                );
            }

            i++;
        }

        // Pop remaining operators
        while (!operators.isEmpty()) {

            if (operators.peek() == '(') {
                throw new IllegalArgumentException(
                    "The parenthesis are not matched correctly!!"
                );
            }

            postfix.append(operators.pop());
            postfix.append(" ");
        }

        return postfix.toString().trim();
    }

    // Evaluates a postfix expression
    @Override
    public int evaluatePostfix(String postfix) {

        Stack<Integer> numbers = new Stack<>();

        String[] tokens = postfix.trim().split("\\s+");

        for (String token : tokens) {

            // Push numbers onto the stack
            if (token.matches("\\d+")) {
                numbers.push(Integer.parseInt(token));
            }

            // Perform mathematical operations
            else if (token.length() == 1 &&
                     isOperator(token.charAt(0))) {

                if (numbers.size() < 2) {
                    throw new IllegalArgumentException(
                        "This postfix expression does not make sense."
                    );
                }

                int right = numbers.pop();
                int left = numbers.pop();

                int result = 0;

                if (token.charAt(0) == '+') {
                    result = left + right;
                }

                else if (token.charAt(0) == '-') {
                    result = left - right;
                }

                else if (token.charAt(0) == '*') {
                    result = left * right;
                }

                else if (token.charAt(0) == '/') {

                    if (right == 0) {
                        throw new ArithmeticException("Cannot divide by zero.");
                    }

                    result = left / right;
                }

                numbers.push(result);
            }

            else {
                throw new IllegalArgumentException(
                    "Wrong postfix token: " + token
                );
            }
        }

        // A valid expression should leave exactly one value
        if (numbers.size() != 1) {
            throw new IllegalArgumentException(
                "Wrong postfix expression."
            );
        }

        return numbers.pop();
    }
}