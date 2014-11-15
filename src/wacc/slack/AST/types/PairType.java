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

	// For use of an ambiguous pairtype
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
		// If we are checking that a pair has the same type as null:
		if (o instanceof PairType) {
			PairType pt = (PairType) o;
			// When we do not care of the type of a certain member of a pair,
			// the type is null

			return typesDoNotConflict(fst, pt.getFst())
					&& typesDoNotConflict(snd, pt.getSnd());
		}
		return false;
	}

	private boolean typesDoNotConflict(Type t1, Type t2) {
		if (t1 == null || t2 == null) {
			return true;
		} else {
			return t1.equals(t2);
		}
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
