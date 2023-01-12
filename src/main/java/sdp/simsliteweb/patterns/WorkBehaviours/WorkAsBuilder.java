package sdp.simsliteweb.patterns.WorkBehaviours;

public class WorkAsBuilder implements WorkBehaviour {

	@Override
	public String work() {
		return "Building things...";
	}

	@Override
	public int getPaid() {
		return 10000;
	}

	@Override
	public String toString() {
		return "Builder";
	}
}
