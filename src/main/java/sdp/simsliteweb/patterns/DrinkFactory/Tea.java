package sdp.simsliteweb.patterns.DrinkFactory;

public class Tea implements DrinkFact {
    @Override
    public String getDrink() {
        return "You ordered Tea";
    }

    @Override
    public int getPrice() {
        return 500;
    }
}
