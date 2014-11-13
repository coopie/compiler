package wacc.slack.AST.types;

import wacc.slack.FilePosition;

public class PairType implements Type {

	private final Type fst, snd;
	private final FilePosition filePos;

	public PairType(Type fst, Type snd, FilePosition filePos) {
		if (fst instanceof PairType) {
			this.fst = new PairType();
		} else {
			this.fst = fst;
		}

		if (snd instanceof PairType) {
			this.snd = new PairType();
		} else {
			this.snd = snd;
		}

		this.filePos = filePos;
	}

	// for use of an ambiguous pairtype
	public PairType() {
		this(null, null, null);
	}

	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}

	@Override
	// TODO: this is a problem possibly
	public boolean equals(Object o) {

		// if we are checking that a pair has the same type as null:
		if (o instanceof PairType) {
			PairType pt = (PairType) o;
			// the type of null or pair is (null,null), this is checking that we are
			// comparing to a null value

			if ((this.fst    == null && this.snd    == null)
			 || (pt.getFst() == null && pt.getSnd() == null)) {
				return true;
			}
		}

		return o instanceof PairType && fst.equals(((PairType) o).fst)
				&& snd.equals(((PairType) o).snd);
	}

	public Type getFst() {
		return fst;
	}

	public Type getSnd() {
		return snd;
	}

	@Override
	public String toString() {
		String s = "pair(";
		if (fst == null) {
			s += "any";
		} else {
			s += fst;
		}
		
		s += ", ";
		
		if (snd == null) {
			s += "any";
		} else {
			s += snd;
		}
		s += ")";
		
		return s;
	}
}
