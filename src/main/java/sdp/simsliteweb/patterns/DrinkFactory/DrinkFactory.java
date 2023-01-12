package sdp.simsliteweb.patterns.DrinkFactory;

public class DrinkFactory {
    public DrinkFact getDrink(String foodType){
        if(foodType == null){
            return null;
        }
        if(foodType.equalsIgnoreCase("Coffee")){
            return new Coffee();

        } else if(foodType.equalsIgnoreCase("Tea")){
            return new Tea();

        } else if(foodType.equalsIgnoreCase("Booze")){
            return new Booze();
        }

        return null;
    }
}
