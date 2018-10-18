// Set datastructure

import java.util.*;

public class Duo {
		private int open;
		private int close;
		
		public Duo () {
			open = 0;
			close = 0;
		}
		
		public Duo (int one, int two) {
			open = one;
			close = two;
		}
		
		public int getOpen () {
			return open;
		}
		
		public int getClose () {
			return close;
		}
		
		public int size() {
			return close-open+1;
		}
		
		public boolean nestedIn (Duo other) {
		// my FindStr doesn't find nested structures --> so I probably won't have a use for this
			if (this.open > other.getOpen () && this.close < other.getClose ()) {
				return true;
			}
			return false;
		}
		
		// adapted from https://www.codexpedia.com/java/java-set-and-hashset-with-custom-class/
		@Override
		public int hashCode() {
			int result = 1;
			result = 31 * result + open;
			result = 31 * result + close;
			return result;
		}

		@Override
		public boolean equals (Object other) {
			if (other == null) 
				return false;
			if (!(other instanceof Duo))
				return false;
			if (other == this)
				return true;
			return (open == ((Duo) other).getOpen () && close == ((Duo) other).getClose ());
		}
		
		public String toString () {
			return "("+Integer.toString(open)+","+Integer.toString(close)+")";
		}
}