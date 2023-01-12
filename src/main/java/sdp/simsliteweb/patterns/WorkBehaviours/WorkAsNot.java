package sdp.simsliteweb.patterns.WorkBehaviours;

public class WorkAsNot implements WorkBehaviour {

	@Override
	public String work() {
		return "Doing nothing...";
	}

	@Override
	public int getPaid() {
		return 0;
	}

	@Override
	public String toString() {
		return "None";
	}
}
