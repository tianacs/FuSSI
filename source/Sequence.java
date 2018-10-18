// Sequence object
// 05 September 2018

public class Sequence {
	private String name;
	private String sequence;

//#####################################################################
	public Sequence (String sequence) {
	// constructor if only a sequence is provided
		this.name = null;
		this.sequence = sequence;
	}
	public Sequence (String name, String sequence) {
	// constructor if sequences with names are provided
		this.name = name;
		this.sequence = sequence;
	}
	
	public Sequence (Sequence original) {
	// copy constructor
		if (original == null) {
			System.out.println ("Fatal error.");
			System.exit (0);
		}
		this.name = original.name;
		this.sequence = original.sequence;
	}
//#####################################################################
	public void setName (String name) {
		this.name = name;
	}
	public void setSeq (String seq) {
		this.sequence = seq;
	}
	public String getName () {
		return name;
	}
	public String getSequence () {
		return sequence;
	}
	
	public boolean equals (Object other) {
		if (other == null) 
				return false;
			if (!(other instanceof Sequence))
				return false;
			if (other == this)
				return true;
			boolean equal_name = ((this.name).equals(((Sequence) other).getName()));
			//System.out.println ("Name is equal: "+equal_name);
			boolean equal_seq = (RemoveGaps.remove (this.sequence)).equals(RemoveGaps.remove(((Sequence) other).getSequence()));
			//System.out.println ("Sequence is equal: "+equal_seq);
			return (equal_name && equal_seq);
	}
	
	public String toString () {
		if (name != null) 
			return name+"\n"+sequence;
		return sequence;		
	}
//#########################################################################		
	public static void main (String[] args) {
		System.out.println ("Testing the Sequence object");
		Sequence test = new Sequence ("name", Translate.DNAtoRNA("AUUCUGUGUCCAACCAGAGAAAGGAGGCCGCAAGCC"));
		//System.out.println (test);
		Structure two = new Structure ("name", "AUUCUGUGUCCAACCAGAGAAAGGAGGCCGCAAGCC");
		two.setStructure (".((((.((......)).))))....(((.....)))");
		//System.out.println (two);
		System.out.println (test.equals (two));
		System.out.println (two instanceof Sequence); 
		//System.out.println (test.getName() == two.getName());
	}
}
