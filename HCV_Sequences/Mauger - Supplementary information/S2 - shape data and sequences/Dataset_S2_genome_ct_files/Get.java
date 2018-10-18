import java.util.*;
import java.io.*;

public class Get{
	public static void main (String[] args) {
		Scanner input = null;
		try {
			input = new Scanner (new FileInputStream("HCV_H77_1a_genome_model.ct"));
		}
		catch (FileNotFoundException e) {
			System.out.println ("File not found.");
			System.exit (0);
		}
		String line = input.nextLine (); //discard first line
		String seq = "";
		int count = 1;
		while (input.hasNextLine()) {
			line = input.nextLine ();
			String[] a = line.split (" ");
			if (count < 10) {
				seq += a[5];
			}
			else if (count < 100) {
				seq += a[4];
			}
			else if (count < 1000) {
				seq += a[3];
			}
			else if (count < 10000) {
				seq += a[2];
			}
			else if (count < 10000) {
				seq += a[1];
			}
			/*for (String s : a) {
				System.out.print (s+"|");
			}*/

			count++;
		}
		System.out.println (seq);
		System.out.println (seq.length ());
		input.close ();
		
		File result = new File ("HCV_H77_1a_genome_model.fas");
		try (BufferedWriter bw = new BufferedWriter (new FileWriter(result,true))){
			bw.write (">HCV_H77_1a_genome");
			bw.write (seq);
			
		}
		catch (IOException e) {
			e.printStackTrace ();	
		}
	}
}