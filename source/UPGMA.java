// Selecting representative sequences to predict structures using a UPGMA clustering method
// 27 September 2018
// Tiana Schwab

// Options to include in future: select a specific sequence -> GUI show names


import java.util.ArrayList;
import java.io.*;
import java.util.Random;

public class UPGMA {
	private ArrayList<Sequence> MSA;			//multiple sequence alignment
	private ArrayList<ArrayList<Float>> table;  //table of differences between sequences
		
	public UPGMA (){
		MSA = null; 	
		table = null;	
	}
	
	public UPGMA (ArrayList<Sequence> MSA) {
		this.MSA = MSA;
		this.table = this.makePairs ();
		}
	
	public ArrayList<ArrayList<Float>> makePairs () {
		// compare each sequence and find the number of differences between them
		ArrayList<ArrayList<Float>> rows = new ArrayList<ArrayList<Float>>();
		for (Sequence s : MSA) {
			ArrayList<Float> columns = new ArrayList<Float> ();
			for (Sequence t : MSA) {
				float diff = difference (s.getSequence(), t.getSequence());
				columns.add (diff);
			}
			rows.add (columns);	
		}
		return rows;
	}
	
	public int difference (String one, String two) {
		// count the differences between two strings
		int diffs = 0;
		for (int p = 0; p < one.length(); p++) {
			if (one.charAt (p) != two.charAt (p)) {
				diffs++;
			}
		}
		return diffs;
	}
	
	public int[] minDiff (ArrayList<ArrayList<Float>> atable) {
		// find the pair with the smalles difference
		Float min = atable.get(0).get(1);	//throw an error here if the supplied table doesn't have two columns to merge
		int[] location = {0,1};
		for (int row = 0; row < atable.size(); row++) {
			for (int column = row+1; column < atable.get(row).size(); column++) {
				if (min > atable.get(row).get(column)) {
					min = atable.get(row).get(column);
					location[0] = row;
					location[1] = column;
				}
			}
		}
		//System.out.println ("Smallest difference: "+min);
		//System.out.println ("Row: "+location[0]);
		//System.out.println ("Column: "+location[1]);
		return location;
	}

	public ArrayList<ArrayList<Float>> merge (ArrayList<ArrayList<Float>> atable) {
		// merge the most similar pairs (smallest difference)
		int[] pos_min = this.minDiff (atable);
		ArrayList<ArrayList<Float>> mRows = new ArrayList<ArrayList<Float>> ();
		for (int r = 0; r < atable.size(); r ++) {
			ArrayList<Float> mColumns = new ArrayList<Float> ();
			for (int c = 0; c < atable.get(r).size(); c++) {
				if (c == pos_min[1] || r == pos_min[1]){
					continue;
				}
				else if (r == c) {
					mColumns.add (0f);
				}
				else if (c == pos_min[0]){
					float x = (Float) atable.get(r).get(c);
					float y = (Float) atable.get(r).get(pos_min[1]);
					mColumns.add ((x+y)/2);
				}
				else if (r == pos_min[0]) {
					float x = (Float) atable.get(r).get(c);
					float y = (Float) atable.get(pos_min[1]).get(c);
					mColumns.add ((x+y)/2);
				}
				else {
					float z = (Float) atable.get(r).get(c);
					mColumns.add(z);
				}
			}
			if (!mColumns.isEmpty()) {
				mRows.add (mColumns);
			}
		}
		return mRows;
	}
	
	public int[] findDivergent (int groups) {
	
		// 1) split sequences of MSA into most divergent groups ([groups] specifies how many)
		//ArrayList<ArrayList<String>> names = new ArrayList<ArrayList<String>> ();
		ArrayList<ArrayList<Integer>> indexes = new ArrayList<ArrayList<Integer>> ();
		// indexes keeps track of which sequences are being grouped together
		int index_MSA = 0;
		for (Sequence s : MSA) {
			ArrayList<Integer> entry = new ArrayList<Integer> (1);
			entry.add (index_MSA++);
			indexes.add (entry);
		}
		//System.out.println (indexes);
		while (table.size () > groups) {
			int[] i = this.minDiff (this.table);
			ArrayList<Integer> x = new ArrayList<Integer> ();
			for (int s : indexes.get(i[0])){
				x.add (s);
			}
			for (int t : indexes.get(i[1])) {
				x.add (t);
			}
			indexes.set(i[0], x);
			indexes.remove(i[1]);
			//System.out.println (names);
			table = this.merge (table);
			//this.printTable (table);
			//System.out.println ("-------------------------------");
		}
		
		// 2) returns [group] names of sequences, one from each group, selected randomly 
		int[] selected = new int[groups];
		int pos = 0;
		for (ArrayList<Integer> group : indexes) {
			int random_position = randomInt (group.size());
			//System.out.println (random_position);
			int sequence = group.get(random_position);
			selected[pos++] = sequence;
		}	
		return selected;
	}
	
	public void printTable (ArrayList<ArrayList<Float>> atable) {
	// print table as a table with columns and rows
		for (ArrayList<Float> row : atable) {
				for (Float column : row) {
					System.out.print (column+" ");
				}
				System.out.println ();
		}
	}
		
	public ArrayList<ArrayList<Float>> getTable () {
		return table;
	}
	
	public static int randomInt (int upperBound) {
		Random random = new Random ();
		return random.nextInt (upperBound);
	}
		
	
	public static void main (String[] args) {
		//String MSA_file = "../Sequences/HCV_Con_Muscle_ali.fas";
		String MSA_file = "../Sequences/GapPractice2.txt";
		ArrayList<Sequence> MSA = Import.readin (MSA_file);
		UPGMA tree = new UPGMA (MSA); //make differece table

		int[] names = tree.findDivergent (5);
		for (int i : names) {
			System.out.println (MSA.get(i));
		}	

	}
}
