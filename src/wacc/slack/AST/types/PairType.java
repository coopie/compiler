package wacc.slack.AST.types;

import wacc.slack.FilePosition;

public class PairType implements Type {

	private final Type fst, snd;
	private final FilePosition filePos;
	
	public PairType(Type fst, Type snd, FilePosition filePos) {
		if (fst instanceof PairType) {
			this.fst = BaseType.T_pair;
		} else {
			this.fst = fst;
		}
		
		if (snd instanceof PairType) {
			this.snd = BaseType.T_pair;
		} else {
			this.snd = snd;
		}
		
		this.filePos = filePos;
	}

	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}
	
	@Override
	public boolean equals(Object o) {
		
		// if we are checking that a pair has the same type as null:
		if (o instanceof PairType) {
			PairType pt = (PairType)o;
			// the type of null is (null,null), this is checking that we are
			// comparing to a null value
			
			if((this.fst    == null && this.snd    == null)
			 ||(pt.getFst() == null && pt.getSnd() == null)) {
				return true;
			}
		}

		return o instanceof PairType &&
				fst.equals(((PairType) o).fst) &&
				snd.equals(((PairType) o).snd);
	}
	
	public Type getFst() {
		return fst;
	}
	
	public Type getSnd() {
		return snd;
	}
	
	@Override
	public String toString() {
		return "pair(" + fst.toString() + ", " + snd.toString() + ")";
	}
}
