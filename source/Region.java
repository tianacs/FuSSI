// Given a specified region within a sequence, extract corresponding region from all sequences in an alignment and write to a a file
// 04/09/2018
// Tiana Schwab

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
				

		Map<String, String> extracts = new HashMap<String, String> ();		//create only unique regions, as requested by ramxl --> fewer sequences, should be faster
		
		int x = 0;
		int no_seq = 0;
		for (Sequence item : MSA) {
			String sub_seq = item.getSequence().substring (structure.getOpen(), structure.getClose()+1);
			if (!(undetermined (sub_seq))){								//skip sequences consisting of only '-' undetermined characters in the specified region
				if (extracts.get (sub_seq) == null) {
					extracts.put (sub_seq, item.getName ());
					no_seq++;
				}
			}
			else {
				System.out.println (item.getName()+" conists only of undetermined characters");
			}
		}
		
		//For RAxML the first line needs to specify the number of sequences + the length of the sequences (eg. 98 52)
		String region_len = Integer.toString (structure.size());
		File file = new File ("extracts_unique_"+run_id+".fas");
		try (BufferedWriter bw = new BufferedWriter (new FileWriter(file))){
	// creates or opens the file (if file already exists it will be overwritten)
	// to append something to a file open the file with FileWriter(filename,true)
			bw.write (no_seq+" "+region_len+"\r\n");
			for (Map.Entry<String, String> entry : extracts.entrySet()) {
				bw.write (entry.getValue ()+"\r\n");
				bw.write (entry.getKey ()+"\r\n");
			}
		}
		catch (IOException e) {
			e.printStackTrace ();	
		}
		//since bw and fw are opened in try block, there is no need to close the files*/
	}
	
	public static boolean undetermined (String string) {
		for (char c : string.toCharArray ()) {
			if (c != '-') {
				return false;
			}
		}
		return true;
	}

	public static void main (String[] args) {
		ArrayList<Sequence> seqs = Import.readin ("../HCV_Sequences/HCV_representative_250seq_coevolution.fasta");
		//positions in Duo (first position is 0)
		Duo d = new Duo (9743, 9790);
		String run_ID = "5BSL3";
		find (seqs, d, run_ID);

	}
}
	
