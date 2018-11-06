// Objects to record the AIC of the NS and the 16S model for each duo tested
// 19 October 2017
// Tiana Schwab

// for a structure (defined by the position it spans in the alignment), calculate its AIC values and the relativeLikelihood of the 16S model

import java.util.*;
import java.io.*;

public class Result {
	private int open;
	private int close;
	private double AIC_NS;
	private double AIC_S16;
	private double relative_likelihood;
	private double scaled_relative_likelihood;
	
	public Result (int open, int close) {
		this.open = open;
		this.close = close;
	}
	
	public void setAIC_NS (double likelihood_NS) {
		AIC_NS = 2*6 - 2*likelihood_NS;		//AIC calculation: set number of free parameters
	}
	
	public void setAIC_S16 (double likelihood_S16) {
		AIC_S16 = 2*7 - 2*likelihood_S16;	//AIC calculation: set number of free parameters
	}
	
	public double relativeLikelihood () {
	//return relative likelihood of the S16 model
		if (AIC_S16 < AIC_NS) {
			double relative_likelihood_NS = Math.exp((AIC_S16 - AIC_NS)/2);
			relative_likelihood = (1/relative_likelihood_NS);
			scaled_relative_likelihood = relative_likelihood/(close - open + 1);
			return relative_likelihood;
		}
		else {
			relative_likelihood = Math.exp((AIC_NS - AIC_S16)/2);
			scaled_relative_likelihood = relative_likelihood/(close - open + 1);
			return relative_likelihood;
		}
	}
	public double getRelativeLikelihood () {
		return relative_likelihood;
	}
	
	public double getScaled () {
		return scaled_relative_likelihood;
	}
	
	public int getOpen () {
		return open;
	}
	
	public int getClose () {
		return close;
	}
	
	public int size () {
		return close-open+1;
	}
	
	
	public String toString () {
		if (AIC_NS == 0 && AIC_S16 == 0) {
			return open+","+close+",ND,ND,-";
		}
		if (AIC_NS == 0) {
			return open+","+close+",ND,"+AIC_S16+",ND";
		}
		if (AIC_S16 == 0) {
			return open+","+close+","+AIC_NS+",ND,ND";
		}
		return open+","+close+","+AIC_NS+","+AIC_S16+","+relative_likelihood;
	}
}
	
class SortbyLikelihood implements Comparator<Result>{

	public int compare (Result a, Result b) {
		/*if (a.getRelativeLikelihood () == b.getRelativeLikelihood()) 
			return 0;
		else if (a.getRelativeLikelihood () < b.getRelativeLikelihood())
			return 1;
		else 
			return -1;
		*/
		// sort results by their relativeLikelihoods scaled by the size of the structure
		if (a.getScaled() == b.getScaled()) 
			return 0;
		else if (a.getScaled() < b.getScaled())
			return 1;
		else 
			return -1;
	}
}
	
class Main {
	public static void main (String[] args) {
		// in actual program just work with the arrayList
		//Result r = new Result (3,4);
		//System.out.println (r);
		
		Scanner input = null;
		try {
			input = new Scanner (new FileInputStream(args[0]));
		}
		catch (FileNotFoundException e) {
			System.out.println ("File not found.");
			System.exit (0);
		}
		
		//Write lines in file into an ArrayList
		ArrayList<Duo> structures = new ArrayList<Duo> ();
		ArrayList<Double> NS = new ArrayList<Double> ();
		ArrayList<Double> S16 = new ArrayList<Double> ();
		while (input.hasNextLine()) {
			String line = input.nextLine ();
			if (line.charAt (0) == '*') {
				String[] position_line = (input.nextLine ()).split(" ");
				int open = Integer.valueOf(position_line[5]);
				int close = Integer.valueOf(position_line[7]);
				structures.add (new Duo (open, close));
				line = input.nextLine (); //sequence line
				line = input.nextLine (); //structure line
				String[] NS_line = input.nextLine ().split(" ");
				String[] S16_line = input.nextLine ().split (" ");
				NS.add (Double.valueOf(NS_line[3]));
				S16.add(Double.valueOf(S16_line[2]));
			} 
		}
		ArrayList<Result> r = new ArrayList<Result> ();
		for (int i = 0; i < structures.size(); i++) {
			Result t = new Result (structures.get(i).getOpen (), structures.get(i).getClose());
			t.setAIC_NS (NS.get(i));
			t.setAIC_S16(S16.get(i));
			t.relativeLikelihood ();
			r.add (t);
		}
		Collections.sort(r, new SortbyLikelihood()); //sort list according to scaled relative likelihood values
		
		/*for (Result p : r) {
			System.out.println (p);
		}
		File result = new File ("Test14Oct_Mauger250.csv");
		try (BufferedWriter bw = new BufferedWriter (new FileWriter(result,true))){
			bw.write ("open, close, AIC_NS, AIC_S16, relative_likelihood_S16, rank\r\n");
			int rank = 1;
			for (Result p : r) {
				bw.write (p.toString()+","+rank++);
				bw.write ("\r\n");
			}
		}
		catch (IOException e) {
			e.printStackTrace ();	
		}*/
		
		//set size of the MSA 
		ArrayList<Double> graph = new ArrayList<Double> (11775);
		for (int i = 0; i < 11775; i++) {
			graph.add (0.0);
		}
		System.out.println (graph);
		for (Result item : r) {
			for (int i = (item.getOpen ()-1); i < item.getClose(); i++) {
				if (graph.get(i) <= item.getRelativeLikelihood ()){
					graph.set (i, item.getRelativeLikelihood());
				}
				//System.out.print (i+" ");
			}
			//System.out.println ();
		}
		System.out.println (graph);
		System.out.println (graph.get(9199));
		System.out.println (graph.get(9208));
		File graph_points = new File ("Graph_HIV_171.csv");
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

