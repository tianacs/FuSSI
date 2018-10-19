// Extract.java

// File to select a subsection of all sequences from a FASTA file and write them to a new file. 

import java.util.ArrayList;
import java.io.*;
import java.util.Random;

public class Extract{
	public static void extract (ArrayList<Sequence> MSA, int open, int close) {
	
		//extract region from aligned file (stored in String[] extracts)
		String[] extracts = new String[MSA.size()];
		int x = 0;
		for (Sequence item : MSA) {
			extracts[x++] = item.getSequence().substring (open, close+1);
		}
		
		File file = new File ("../HIV_Sequences/HIV_rep_171_5UTR.fas");
		try (BufferedWriter bw = new BufferedWriter (new FileWriter(file))){
	// creates or opens the file (if file already exists it will be overwritten)
	// to append something to a file open the file with FileWriter(filename,true)
			for (int i = 0; i < extracts.length; i++) {
				bw.write(MSA.get(i).getName()+"\r\n");
				bw.write(extracts[i]+"\r\n");
			}
		}
		catch (IOException e) {
			e.printStackTrace ();	
		}
		//since bw and fw are opened in try block, there is no need to close the files*/
	}
	
	public static void shuffle (ArrayList<Sequence> MSA, int open, int close) {
		String[] extracts = new String[MSA.size()];
		int x = 0;
		for (Sequence item : MSA) {
			extracts[x++] = item.getSequence().substring (open, close+1);
		}
		String[] shuffled = new String[MSA.size()];
		
		//String[] w = {"Hello", "apple", "fiver", "oneto"};
		//String[] s = new String[w.length];
		for (int i = 0; i < shuffled.length; i++) {
			shuffled[i] = "";
		}

		//String w = "hello";
		//String s = "";
		
		Random random = new Random ();
		for (int i = 0; i < extracts[0].length (); i++) {
			int random_pos = random.nextInt (extracts[0].length());
			for (int c = 0; c < extracts.length; c++) {
				shuffled[c] += extracts[c].charAt (random_pos);
			}
			//s += w.charAt (random_pos);
		}
		
		for (String y : shuffled) {
			System.out.println (y);
		}
		
	}
	
	public static void main (String[] args) {
		ArrayList<Sequence> MSA = Import.readin ("../HIV_Sequences/HIV_rep_171.fas");
		extract (MSA, 0, 1000);
		//shuffle (MSA, 0, 40);	
	}
	}	
