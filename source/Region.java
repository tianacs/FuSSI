// Extract corresponding regions from a file
// 04/09/2018

/* 
Input:	- a structure sequence (or a structure object - already predicted)
		- a multiple sequence alignment (sequence used for structure prediction is assumed to be first (position 0))
Output:	- a list of corresponding positions of the structure seq in the aligned sequences
		- a list of the extracted regions
*/

import java.util.*;
import java.io.*;

public class Region {
	public static void find (ArrayList<Sequence> MSA, Duo structure, String run_id) {
				
		//extract region from aligned file (stored in String[] extracts)
		//String[] extracts = new String[MSA.size()];
		Map<String, String> extracts = new HashMap<String, String> ();		//create only unique regions, as requested by ramxl --> fewer sequences, should be faster
		
		int x = 0;
		int no_seq = 0;
		for (Sequence item : MSA) {
			//extracts[x++] = item.getSequence().substring (gap_begin, gap_end+1);
			String sub_seq = item.getSequence().substring (structure.getOpen(), structure.getClose()+1);
			if (extracts.get (sub_seq) == null) {
				extracts.put (sub_seq, item.getName ());
				no_seq++;
			}
			//extracts.add (item.getSequence().substring (gap_begin, gap_end+1));
		}
		
		//For RAxML the first line needs to specify the number of sequences + the length of the sequences (eg. 98 52)
		//String no_seq = Integer.toString (MSA.size());
		//int check = extracts.length;
		int check = extracts.size ();
		//System.out.println (no_seq+" "+check);
		//String region_len = Integer.toString (extracts[0].length ());
		String region_len = Integer.toString (structure.size());
		
		//File file = new File ("extracts.fas");
		File file = new File ("extracts_unique_"+run_id+".fas");
		try (BufferedWriter bw = new BufferedWriter (new FileWriter(file))){
	// creates or opens the file (if file already exists it will be overwritten)
	// to append something to a file open the file with FileWriter(filename,true)
			bw.write (no_seq+" "+region_len+"\r\n");
			//for (int i = 0; i < extracts.length; i++) {
			for (Map.Entry<String, String> entry : extracts.entrySet()) {
				//bw.write(MSA.get(i).getName()+"\r\n");
				//bw.write(extracts[i]+"\r\n");
				bw.write (entry.getValue ()+"\r\n");
				bw.write (entry.getKey ()+"\r\n");
			}
		}
		catch (IOException e) {
			e.printStackTrace ();	
		}
		//since bw and fw are opened in try block, there is no need to close the files*/
	}

	public static void main (String[] args) {
		
		//Fasta items = Fasta.readin ("../Sequences/HCV_Con_Muscle_ali.fas");
		ArrayList<Sequence> seqs = Import.readin ("../Sequences/HCV_1b_97seq_codon_aligned.fasta");
		//ArrayList<String> seqs = items.getSeqs();
		
		String consensus_gapless = RemoveGaps.remove(seqs.get(0).getSequence());
		Sequence cons = new Sequence (seqs.get(0).getName (), consensus_gapless);
		
		String structure = "UGCGUGGAACCAGAGAAGGGGGGCCGCA";
		//"UUUCUGCGUCCAACCAGAGAAGGGGGGCCGCAAGCC";
		//String structure = "GCCAGCCCCCGAUUGGGGGCGACACUCCA";
			
		Duo d = new Duo(3709,3854);
		find (seqs, d, "TestingError2");
		/*
		//Testing Map
		Map<String, String> extracts = new HashMap<String, String> ();
		extracts.put ("Name1", "seq1");
		extracts.put ("Name2", "seq2");
		extracts.put ("Name3", "seq3");
		extracts.put ("Name4", "seq3");
		
		for (Map.Entry<String, String> entry : extracts.entrySet()) {
			System.out.println (entry.getKey ());
			System.out.println (entry.getValue ());			
		}
		if (extracts.get ("Name5") != null) {
			System.out.println ("already in ");
		}*/
	}
}
	
