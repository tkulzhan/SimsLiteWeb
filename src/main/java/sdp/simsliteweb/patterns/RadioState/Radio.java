package sdp.simsliteweb.patterns.RadioState;

public class Radio {
    private RadioState state;

    public Radio(RadioState state) {
        this.state = state;
    }

    public void setState(RadioState state) {
        this.state = state;
    }

    public RadioState getState() {
        return state;
    }
}
