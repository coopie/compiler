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
	
	public boolean equals(Type t) {
		
		// if we are checking that a pair has the same type as null:
		if ((this.snd == null && this.snd ==  null))
				{
			return t instanceof PairType;
		} else {
			return t instanceof PairType &&
					fst.equals(((PairType) t).fst) &&
					snd.equals(((PairType) t).snd);
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
		return "pair(" + fst.toString() + ", " + snd.toString() + ")";
	}
}
