package sdp.simsliteweb.patterns.RadioState;

public class OnState implements RadioState {
    @Override
    public String switchRadio() {
        return "Radio is On";
    }

    @Override
    public String toString() {
        return "On";
    }
}
