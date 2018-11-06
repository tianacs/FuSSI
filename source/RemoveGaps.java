// Remove gaps from an alignment file or from a single sequence String containing gaps
// 04/09/2018
// Tiana Schwab

// Remove gaps from a single sequence (provided as String) = RemoveGaps.remove (String entry)
// Remove gaps from all sequences in a list (ArrayList) = RemoveGaps.remove (ArrayList<String> entry)

import java.util.ArrayList;

public class RemoveGaps {

	public static String remove (String entry) {
	//remove gaps (-) from a single sequence
		String gapless = "";
		for (int i = 0; i < entry.length(); i++) {
			char c = entry.charAt (i);
			if (c != '-') {
				gapless += c; 
			}
		}
		return gapless;
	}
	
	public static ArrayList<Sequence> remove (ArrayList<Sequence> entry) {
	// remove gaps from all sequences in a list of sequences
		ArrayList<Sequence> gapless = new ArrayList<Sequence>(entry.size());
		for (Sequence element : entry) {
			Sequence item = new Sequence (element.getName(), remove(element.getSequence()));
			gapless.add (item);
		}
		return gapless;
	}

	public static void main (String[] args) {
		String file = "../Sequences/HCV_Con_Muscle_ali.fas";
		ArrayList<Sequence> lis = Import.readin (file);
		ArrayList<Sequence> gapless = remove(lis);
		System.out.println (gapless);
	}
}
