// Predicting the secondary structure of a sequence
// 04 September 2018
// Tiana Schwab

/*	Input: 	String containing location and file name of sequence to predict
	Output:	A structure file containing (the name,) sequence and structure of the sequnce supplied
	
	ISSUES	- files containing just a sequence do not work because predict () will not find a > to find the sequence
*/

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class DotBracket {
	public static void RNAfold (String filename, String run_id) {
		try {
			Runtime rt = Runtime.getRuntime();
			// run RNAfold
			System.out.println ("Running RNAfold...");
			Process p = rt.exec ("RNAfold --noPS --outfile=RNAfold_output_"+run_id+" -i "+filename);
			p.waitFor ();
		}
		catch (Exception e) {
			System.out.println (e.toString());
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Structure> extract (String run_id) {
		//read in lines of a textfile entry (location+file_name)
		Scanner input = null;
		try {
			input = new Scanner (new FileInputStream("RNAfold_output_"+run_id));
		}
		catch (FileNotFoundException e) {
			System.out.println ("RNAfold_output file not found.");
			System.exit (0);
		}
		ArrayList<Structure> predicted_structures = new ArrayList<Structure> ();
		while (input.hasNextLine ()) {
		// From file find sequence and predicted structure
			String line = input.nextLine ();
			if (line.charAt(0)=='>') {	
				String name = line;
				String seq = input.nextLine();
				Structure structure = new Structure (name, seq);
				
				//remove the MFE value
				String[] structure_line = input.nextLine().split(" ");
				structure.setStructure(structure_line[0]);
				
				//System.out.println (structure);
				predicted_structures.add (structure);
			}
		}
		return predicted_structures;
	}	

//#############################################################################
	public static void main (String[] args) {
		System.out.println ("############### DotBracket.java #################");
		
		String file = "../Sequences/HCV_Con1b_region.seq";
		//RNAfold (file);
		//System.out.println (extract ());
		
		
	}
	
}

