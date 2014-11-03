package wacc.slack.AST.types;

import wacc.slack.AST.visitors.ASTVisitor;

public class PairType implements Type {

	Type fst;
	Type snd;
	
	public PairType(Type fst, Type snd) {
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

	@Override
	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub
		
	}
}
