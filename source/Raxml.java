// Running RAxML and extracting likelihood values from RAxML_info.* files
// 07 September 2018
// Tiana Schwab

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import java.lang.*;

public class Raxml {
	public static void AIC (double GTR, double S16) {
	// print AIC scores (according to Savill 2001), assumin RAxML score is a log-likelihood
		double AIC1 = -1*GTR+6;
		double AIC2 = -1*S16+7;
		System.out.println (AIC1);
		System.out.println (AIC2);
	}

	//public static double getValue (String file) {
	public static String getValue (String file) {
	// extract likelihood score calculated by raxml from RAxML_info.* file
		String likelihood = null;
		//BufferedReader input = null;
		try {
			BufferedReader input = new BufferedReader (new FileReader(file));
			for (String line = input.readLine (); line != null; line = input.readLine ()) {
				if (line.contains ("Final GAMMA-based Score of best tree")) {
					likelihood = line.split(" ")[6];
					System.out.println ("Best score in "+file+" file is: "+likelihood);
				}
			}
			input.close();
			
		}
		catch (FileNotFoundException e) {
			System.out.println ("RaxML_info file not found.");
			return null;
		}
		catch (IOException e) {
			System.out.println ("something went wrong"); //when is IOException caused by readLine()?
		}
		finally {
			if (likelihood == null) {
				System.out.println ("couldn't extract");
				return null;
			}
			return likelihood;
		}
	}
	
	public static void raxml (String run_id) {
		//Set raxml parameters
		try {
		
			Runtime rt = Runtime.getRuntime();
			System.out.println ("Running raxml with NS model");
			//Process p = rt.exec ("raxmlHPC -m GTRGAMMA -p 14567 -s extracts.fas -n NS_30Sep");
			Process p = rt.exec ("raxmlHPC -m GTRGAMMA -p 14567 -s extracts_unique_"+run_id+".fas -n NS_"+run_id);
			p.waitFor ();
			System.out.println ("Running raxml with 16S model");
			Process q = rt.exec ("raxmlHPC -m GTRGAMMA -p 14567 -S consensus_structure_"+run_id+".txt -s extracts_unique_"+run_id+".fas -n 16S_"+run_id);
			q.waitFor ();	
		}
		catch (Exception e) {
			System.out.println ("RAxML not found");
			System.out.println (e.toString());
			e.printStackTrace();
		}
	}

	public static void main (String[] args) {
		//raxml ();
		double NS = Double.valueOf(getValue ("RAxML_info.NS"));
		//double S16 = getValue("RAxML_info.16S");
		//double diff = S16-NS;
		System.out.println (NS);
	
	}
}
