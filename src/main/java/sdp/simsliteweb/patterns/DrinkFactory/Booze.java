package sdp.simsliteweb.patterns.DrinkFactory;

public class Booze implements DrinkFact {
    @Override
    public String getDrink() {
        return "You ordered Booze";
    }

    @Override
    public int getPrice() {
        return 1000;
    }
}
