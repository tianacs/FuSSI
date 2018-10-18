// Translate a DNA sequence to RNA or RNA to DNA
// 04/09/2018

/*	
Input:	- A sequence as a string + specify DNAtoRNA or RNAtoDNA
Output:	- String of the translated sequence
*/

public class Translate {
	public static String DNAtoRNA (String entry) {
		String output = "";
		for (int i = 0; i < entry.length (); i++) {
			if (entry.charAt (i) == 'T') {
				output += 'U';
			}
			else {
				output += entry.charAt (i);
			}
		}
		return output;
	}
	
	public static String RNAtoDNA (String entry) {
		String output = "";
		for (int i = 0; i < entry.length (); i++) {
			if (entry.charAt (i) == 'U') {
				output += 'T';
			}
			else {
				output += entry.charAt (i);
			}
		}
		return output;	
	}
	
	public static void main (String[] args) {
		String DNA = "TTTCTGCGTCCAACCAGAGAAGGGGGGCCGCAAGCC";
		String RNA = "UUUCUGCGUCCAACCAGAGAAGGGGGGCCGCAAGCC";
		
		System.out.println (DNAtoRNA (DNA));
		System.out.println (DNAtoRNA (DNA).equals (RNA));
		System.out.println (RNAtoDNA (RNA));
		System.out.println (RNAtoDNA (RNA).equals (DNA));
	}
}
