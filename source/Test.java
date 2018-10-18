import  java.util.*;
public class Test {
	public static void main (String[] args) {
		ArrayList<Sequence> MSA = Import.readin ("../HCV_representative_250seq_coevolution.fasta");
		//String name = ">Some;thin g_Na:m,e_is_he	re'_(F{DA}F[B])";
		char[] prohibited = {'\r', ' ', '	', ':', ',', '(', '(', ';', ']', '[', '\''};
		for (Sequence seq : MSA) {
			for (char c : prohibited) {
				if (seq.getName().contains (Character.toString(c))) {
					System.out.println (seq.getName ()+" contains a prohibited character");
				}
			}
		}
	}
}