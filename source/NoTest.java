import java.util.*;
import java.io.*;

public class NoTest {
	public static void cleanUp (String run_id) {
	// remove all RAxML files
		String[] unneccessary_files = {"bestTree","log","parsimonyTree", "result", "info"};
		System.out.println ("Cleaning up...\n");
		for (String i : unneccessary_files) {
			File del_file1 = new File ("RAxML_"+i+".NS_"+run_id);
			//can give location too
			del_file1.delete ();
			File del_file2 = new File ("RAxML_"+i+".16S_"+run_id);
			del_file2.delete ();
		}
		File del_file = new File ("consensus_structure_"+run_id+".txt");
	}
	
	public static void main (String[] args) {
		ArrayList<Sequence> MSA = Import.readin("../HCV_Sequences/HCV_representative_250seq_coevolution.fasta");
		String run_ID = "lessTen";
		String max_size = "400";
		
		// Check for prohibited characters in taxon names (necessary for RAxML)
		char[] prohibited = {'\r', ' ', '	', ':', ',', '(', '(', ';', ']', '[', '\''};
		int number_seq = 1;
		for (Sequence seq : MSA) {
			number_seq++;
			for (char c : prohibited) {
				if (seq.getName().contains (Character.toString(c))) {
					System.out.println ("Illegal character in sequence "+number_seq+": "+seq.getName());
					System.out.println ("Illegal characters: tabulators, carriage returns, spaces, \":\", \",\", \"(\", \"(\", \";\", \"]\", \"[\", \"'\"");
					System.exit (0);
				}
			}
		}
		
		System.out.println ("Your file contains "+MSA.size()+" sequences");
		int[] sequences = {194, 231, 25, 33, 52};
		int groups = 5;
		
		File file = new File ("Representative_Sequences_"+run_ID);
		try (BufferedWriter bw = new BufferedWriter (new FileWriter (file))) {
			for (int i : sequences) {
				bw.write (MSA.get(i).getName ()+"\r\n");
				bw.write (RemoveGaps.remove (MSA.get(i).getSequence())+"\r\n");
			}
			bw.close();
		}
		catch (IOException e) {
			e.printStackTrace ();
		}
		System.out.println ("Check if representative sequences match");
		DotBracket.RNAfold ("Representative_Sequences_"+run_ID, run_ID);
		System.out.println ("Check if RNAoutput files match");
		ArrayList<Structure>fold = DotBracket.extract (run_ID);
		System.out.println ("Extracted structures.");
		ArrayList<ArrayList<Integer>> alignment_pos = new ArrayList<ArrayList<Integer>> (groups);
		for (int i = 0; i < groups; i++) {
			ArrayList<Integer> gp = new ArrayList<Integer> ();
			int index = 0;
			for (char c : MSA.get(sequences[i]).getSequence().toCharArray ()){
				if (c != '-') {
					gp.add (index);
				}
				index++;
			}
			alignment_pos.add (gp);
		}
		Set<Duo> duos = new HashSet<Duo> (); 
		int index = 0;
		int seq = 0;
		for (Structure s : fold) {
			//ArrayList<Duo> bounds = .subdivide(s.getStructure()); //set of positions w/o gaps
			ArrayList<Duo> alignment_bounds = FindStr.convertToGapped (FindStr.subdivide(s.getStructure()), alignment_pos.get(seq++));
			for (Duo d : alignment_bounds) {
				// limit size of structures searched
				if (d.size () <= Integer.valueOf (max_size)) {
					duos.add (d);
				}
			}
		}
		
		// ********************************************************
		Scanner input = null;
		try {
			input = new Scanner (new FileInputStream("../HCV_Sequences/Output/Ranked_Likelihoods_Mauger250.csv"));
		}
		catch (FileNotFoundException e) {
			System.out.println ("File not found.");
			System.exit (0);
		}
		ArrayList<String> listy = new ArrayList<String> ();
		String line2 = input.nextLine ();
		while (input.hasNextLine()) {
			line2 = input.nextLine ();
			if (!(line2.equals (""))) {
				String[] l = line2.split (",");
				listy.add (l[1]);
			}
		}
		input.close ();
		
		System.out.println (listy);
		int[] open = new int[listy.size ()];
		int[] close = new int[listy.size ()];
		int index2 = 0;
		for (String s : listy) {
			String one = "";
			String two = "";
			char[] c = s.toCharArray();
			System.out.println (c);
			boolean lever = true;
			for (char no : c) {
				if (no == ':') {
					lever = false;
					continue;
				}
				if (lever) {
					one += no;
				}
				else {
					two += no;
				}
				
			}
			System.out.println (one);
			System.out.println (two);
			open[index2] = Integer.valueOf (one);
			close[index2] = Integer.valueOf (two);
			index2++;
		}
		ArrayList<Duo> nocheck = new ArrayList<Duo> ();
		for (int i = 0; i < open.length; i++) {
			nocheck.add (new Duo(open[i], close[i]));
		}
		nocheck.add (new Duo(5170, 5181));
		//************************************************************
		
		int count = 0;
		//ArrayList<Duo> support_for_structure = new ArrayList<Duo> ();
		ArrayList<Result> support_for_structure = new ArrayList<Result> ();
		//System.out.println (duos);
		for (Duo structure : duos) {
			boolean in = false;
			for (Duo already : nocheck) {
				if (structure.equals (already)) {
					in = true;
					break;
				}
			}
			if (in) {
				System.out.println ("don't check"+ structure);
				continue;
			}
			System.out.println ("check: "+structure);
			System.out.println ((++count)+"/"+duos.size()+": "+structure);
			Region.find (MSA, structure, run_ID);
			//run RNAalifold to get a consensus structure
			String consensus_sequence = "";
			String consensus_structure = "";
			try {
				Runtime rt = Runtime.getRuntime();
				System.out.println ("Getting consensus structure...");
				Process p = rt.exec ("RNAalifold --noPS extracts_unique_"+run_ID+".fas");
				p.waitFor ();
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while ((line = reader.readLine ()) != null) {
					consensus_sequence = line; //do I need this somewhere?
					System.out.println (consensus_sequence);
					String[] structure_line = reader.readLine().split(" ");
					consensus_structure = structure_line[0];					
				}
			}
			catch (Exception e) {
				System.out.println (e.toString());
				e.printStackTrace();
			}
			System.out.println (consensus_structure);
			//prepare structure file
			File structure_file = new File ("consensus_structure_"+run_ID+".txt");
			try (BufferedWriter bw = new BufferedWriter (new FileWriter (structure_file))) {
				bw.write (consensus_structure);
			}
			catch (IOException e) {
					e.printStackTrace (); 
			}
			System.out.println ("Structure file for RAxML written");
			//run raxml and get values
			Raxml.raxml (run_ID);			
			System.out.println ("Getting values...");
			String NS = Raxml.getValue ("RAxML_info.NS_"+run_ID);
			String S16 = Raxml.getValue("RAxML_info.16S_"+run_ID);
			
			if (NS == null) {
				System.out.println ("RAxML couldn't run GTR model");
				Result x = new Result (structure.getOpen()+1, structure.getClose ()+1);
				if (S16 != null) {
					x.setAIC_S16 (Double.valueOf(S16));
				}
				support_for_structure.add (x);
				cleanUp (run_ID);
				//support_for_structure.add (x);
				continue;
			}
			if (S16 == null) {
				System.out.println ("RAxML couldn't run secondary structure model.");
				Result x = new Result (structure.getOpen()+1, structure.getClose ()+1);
				if (NS != null) {
					x.setAIC_NS (Double.valueOf(NS));
				}
				support_for_structure.add (x);
				cleanUp (run_ID);
				//support_for_structure.add (x);
				continue;
			}
			double NS_value = Double.valueOf(NS);
			double S16_value = Double.valueOf(S16);
			double ans = Math.exp(Double.valueOf(S16)-Double.valueOf(NS));
			
			Result x = new Result (structure.getOpen ()+1, structure.getClose()+1);
			x.setAIC_NS (NS_value);
			x.setAIC_S16(S16_value);
			x.relativeLikelihood ();
			support_for_structure.add (x);
			
			if (Double.valueOf(NS) >= Double.valueOf(S16)) {
				System.out.println ("No support");
				//cleanUp (run_ID);
				//continue;
			}
			else if (ans < 10) {
				System.out.println ("Not sufficiently more likely");
				//cleanUp (run_ID);
				//continue;
			}
			//support_for_structure.add (structure);
			File result = new File ("Likelihoods_"+run_ID+".txt");
			try (BufferedWriter bw = new BufferedWriter (new FileWriter(result,true))){
			// creates or opens the file (if file already exists it will be overwritten)
			// to append something to a file open the file with FileWriter(filename,true)
				bw.write ("*******************************************************\r\n");
				bw.write ("Likelihood for structure at position "+(structure.getOpen()+1)+" to "+(structure.getClose()+1)+"\r\n");
				bw.write (consensus_sequence+"\r\n");
				bw.write (consensus_structure+"\r\n");
				bw.write ("Likelihood GTR NS: "+NS+"\r\n");
				bw.write ("Likelihood 16S: "+S16+"\r\n");
				bw.write ("Constrained 16S model is "+String.format("%.2f", ans)+"times more likely than NS model\r\n");
			}
			catch (IOException e) {
				e.printStackTrace ();	
			}
						
			// remove all RAxML files
			cleanUp (run_ID);
		}
		Collections.sort(support_for_structure, new SortbyLikelihood()); //sort list according to *scaled* relative likelihoods of S16
		File result = new File ("Ranked_Likelihoods_"+run_ID+".csv");
		try (BufferedWriter bw = new BufferedWriter (new FileWriter(result,true))){
			bw.write ("section,, AIC_NS, AIC_S16, relative_likelihood_S16, rank\r\n");
			int rank = 1;
			for (Result r : support_for_structure) {
				bw.write (r.toString()+","+rank++);
				bw.write ("\r\n");
			}
		}
		catch (IOException e) {
			e.printStackTrace ();	
		}
		//once all done delete Represenative_Structures, RNAfold_output
		//should delete likelihood.txt file too, otherwise next run justs adds more to it
		int size_MSA_sequence = MSA.get(0).getSequence().length();
		ArrayList<Double> graph = new ArrayList<Double> (size_MSA_sequence);
		for (int i = 0; i < size_MSA_sequence; i++) {
			graph.add (0.0);
		}
		for (Result item : support_for_structure) {
			for (int i = (item.getOpen ()-1); i < item.getClose(); i++) {
				if (graph.get(i) <= item.getRelativeLikelihood ()){
					graph.set (i, item.getRelativeLikelihood());
				}
			}
		}
		File graph_points = new File ("Graph_"+run_ID+".csv");
		try (BufferedWriter bw = new BufferedWriter (new FileWriter (graph_points))) {
			bw.write ("Position,Relative_Likelihood\r\n");
			for (int i = 0; i < graph.size (); i++) {
				bw.write ((i+1)+","+graph.get(i)+"\r\n");
			}
		}
		catch (IOException e) {
			e.printStackTrace ();
		}
		
	}
}
