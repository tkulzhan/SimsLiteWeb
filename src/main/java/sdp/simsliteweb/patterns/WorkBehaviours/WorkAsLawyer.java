package sdp.simsliteweb.patterns.WorkBehaviours;

public class WorkAsLawyer implements WorkBehaviour {

	@Override
	public String work() {
		return "Doing lawyer stuff...";
	}

	@Override
	public int getPaid() {
		return 20000;
	}

	@Override
	public String toString() {
		return "Lawyer";
	}
}
