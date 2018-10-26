// Runtime analysis of FuSSI

import java.io.*;
import java.util.*;


public class Runtime {
	public static void main (String[] args_) {
	
		// Test MSA of sequence length 200, 500 (y), 1000, 5000 (y), 10000 (y)
		String[] files = {};
		
		for (int i = 0; i < files.length; i++) {
			long startTime = System.currentTimeMillis ();
			try {
				Runtime rt = Runtime.getRuntime();
				Process p = rt.exec ("java -jar FuSSI.jar ../Runtime_analysis/"+files[i]+" no"+i+" 400");
				p.waitFor();
			}
			catch (Exception e) {
				System.out.println (e.toString());
				e.printStackTrace();
			}
			long stopTime = System.currentTimeMillis ();
			long elapsedTime = stopTime- startTime;
			System.out.println (elapsedTime);
			System.out.println (elapsedTime/1000.0+" Seconds");
			System.out.println (elapsedTime/1000.0/60+" Minutes");
			System.out.println (elapsedTime/1000.0/60/60+" Hours");
		}
			
		
	}
}