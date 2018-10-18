// Stack datastructure 
// based off Chapter 15 Absolute Java by Walter Savitch

import java.util.NoSuchElementException;
import java.util.ArrayList;

public class Stack {
	private class Node {
		private int item;
		private Node link;
		
		public Node () {
			item = 0;
			link = null;
		}
		
		public Node (int item, Node link) {
			this.item = item;
			this.link = link;
		}
	}
	private Node start;
	
	public Stack () {
		start = null;
	}
	
	public void push (int entry) {
		start = new Node (entry, start); 
	}
	
	public int pop () {
		if (start == null) {
			throw new IllegalStateException();
		}
		else {
			int result = start.item;
			start = start.link;
			return result;
		}
	}
	
	public boolean isEmpty () {
		return (start == null); 
	}
	
	public static void main (String[] args) {
		Stack stack = new Stack ();
		//String struc =    "...(((......)))..(((......)))..((((((....)))(((......)))(((......)))))).....(((((.....)))))...";
		String struc = "(((....)))(((......)))(((......)))";
		//String struc="........";
		boolean lever = false;
		int position = -1;
		ArrayList<Duo> structures = new ArrayList<Duo>();
		for (char c : struc.toCharArray ()){
			position++;
			int site = 0;
			if (c == '(' && !lever) {
			// start of a new structure
				lever = true;
				stack.push (position);
			}
			else if (c == '(' && lever) {
				stack.push (position);
			}
			else if (c == ')') {
				site = stack.pop ();
			}
			if (stack.isEmpty () && lever) {
			// end of a structure
				structures.add (new Duo(site, position));
				lever = false;
			}
		}
		System.out.println (structures);
		
	}
}

