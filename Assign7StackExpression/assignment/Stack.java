package assignment;

import java.util.EmptyStackException;

public class Stack<T> implements StackInterface<T>, ExpressionEvaluatorInterface{

	private Node<T> top;
    private int size;

    // Helper Node class
    private static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }

    public Stack() {
        this.top = null;
        this.size = 0;
    }
	@Override
	public void push(T item) {
		// TODO Auto-generated method stub
		Node<T> newNode = new Node<>(item);
        newNode.next = top; // Point new node to the old top
        top = newNode;      // Set new node as the top
        size++;
	}

	@Override
	public T pop() {
		// TODO Auto-generated method stub
		 if (isEmpty()) {
	            throw new EmptyStackException();
	        }
	        T data = top.data;
	        top = top.next; // Move top pointer down
	        size--;
	        return data;
	}

	@Override
	public T peek() {
		// TODO Auto-generated method stub
		if (isEmpty()) {
            throw new EmptyStackException();
        }
		return top.data;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		if (isEmpty()) {
            return true;
        }
		else {
			return false;
		}
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	
	//Infix to postfix code
	@Override
	public String infixToPostfix(String infix) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public int evaluatePostfix(String postfix) {
		// TODO Auto-generated method stub
		return 0;
	}

}
