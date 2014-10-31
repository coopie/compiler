package wacc.slack.AST.types;

public class PairType implements Type {

	Type fst;
	Type snd;
	
	PairType(Type fst, Type snd) {
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
	}
}
