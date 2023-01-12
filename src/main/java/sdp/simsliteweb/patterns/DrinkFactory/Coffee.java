package sdp.simsliteweb.patterns.DrinkFactory;

public class Coffee implements DrinkFact {
    @Override
    public String getDrink() {
        return "You ordered Coffee";
    }

    @Override
    public int getPrice() {
        return 1500;
    }
}
