package wacc.slack.AST.types;

public class PairType implements Type {

	private final Type fst, snd;
	private final int linePos, charPos;
	
	public PairType(Type fst, Type snd, int linePos, int charPos) {
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
		
		this.linePos = linePos;
		this.charPos = charPos;
	}

	@Override
	public int getLine() {
		return linePos;
	}

	@Override
	public int getCharColumn() {
		return charPos;
	}
	
	public boolean equals(Type t) {
		return t instanceof PairType &&
				fst.equals(((PairType) t).fst) &&
				snd.equals(((PairType) t).snd);
	}
	
	public Type getFst() {
		return fst;
	}
	
	public Type getSnd() {
		return snd;
	}
}
