// Runtime analysis of FuSSI

import java.io.*;
import java.util.*;


public class Taketime {
	public static void main (String[] args_) {
	
		// Test MSA of sequence length 200, 500 (y), 1000, 5000 (y), 10000 (y)
		//String[] files = {"HIV_171_5000.fas"};
		String[] files = {"HIV_70_500.fas", "HIV_100_500.fas","HCV_247_500.fas"};
		
		for (int i = 0; i < files.length; i++) {
			System.out.println ("Running "+files[i]);
			long startTime = System.currentTimeMillis ();
			try {
				Runtime rt = Runtime.getRuntime();
				Process p = rt.exec ("java -jar FuSSI.jar ../Runtime_analysis/"+files[i]+" time"+i+" 400");
				p.waitFor();
			}
			catch (Exception e) {
				System.out.println (e.toString());
				e.printStackTrace();
			}
			long stopTime = System.currentTimeMillis ();
			long elapsedTime = stopTime- startTime;
			System.out.println (elapsedTime+" Milliseconds");
			System.out.println (elapsedTime/1000.0+" Seconds");
			System.out.println (elapsedTime/1000.0/60+" Minutes");
			System.out.println (elapsedTime/1000.0/60/60+" Hours");
		}
			
		
	}
}