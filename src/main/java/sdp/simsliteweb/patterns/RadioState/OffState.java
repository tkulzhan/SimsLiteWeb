package sdp.simsliteweb.patterns.RadioState;

public class OffState implements RadioState {
    @Override
    public String switchRadio() {
        return "Radio is Off";
    }

    @Override
    public String toString() {
        return "Off";
    }
}
