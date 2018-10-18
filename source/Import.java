// import sequences from a fasta file and create a list of Sequence objects
// 05 September 2018

/* Import.readin creates a list of sequence objects. Each individual sequence object contains the name of the sequence (item.getName()) and the sequence (item.getSequence())
*/

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Import {
	public static ArrayList<Sequence> readin (String entry) {
		//read in lines of a textfile entry (location+file_name)
		Scanner input = null;
		try {
			input = new Scanner (new FileInputStream(entry));
		}
		catch (FileNotFoundException e) {
			System.out.println ("File not found.");
			System.exit (0);
		}
		
		//Write lines in file into an ArrayList
		ArrayList <String> listy = new ArrayList<String>();
		while (input.hasNextLine()) {
			String line = input.nextLine ();
			if (!(line.equals ("")))
				listy.add (line);
		}
		input.close ();

		ArrayList<Sequence> output = new ArrayList<Sequence> ();
		
		for (String item:listy) {
			if (item.charAt(0) == '>') {
				// merge lines following the tile (>) line into a single string
				String fullseq = "";
				int position = listy.indexOf (item) + 1;
				while ((position < listy.size ()) && !(listy.get(position).charAt (0) == '>')) {
					fullseq += listy.get (position);
					position ++;
				}
				Sequence thing = new Sequence (item, Translate.DNAtoRNA(fullseq));
				output.add (thing);
			}
		}

		return output;
	}
	
	public static void main (String[] args) {
		String file = "../Sequences/HCV_representative_250seq_coevolution.fasta";
		ArrayList<Sequence> lis = readin (file);
		System.out.println (lis);
		System.out.println (lis.size());
		for (int i = 0; i < lis.size(); i++) {
			for (int j = 0; j < lis.size(); j++) {
				if (i == j) {
					continue;
				}
				if (lis.get(i).equals (lis.get(j))) 
					System.out.println (lis.get(i)+" equals "+lis.get(j));
			}
		}
		for (int i = 0; i < lis.size(); i++) {
			for (int j = 0; j < lis.size(); j++) {
				if (i == j) {
					continue;
				}
				if (lis.get(i).getName().equals (lis.get(j).getName())) 
					System.out.println ("Equal names: "+lis.get(i).getName());
			}
		}
		
	}
}
