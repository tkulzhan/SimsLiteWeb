package sdp.simsliteweb.patterns.FoodDecorators;

public class Dish implements Food {

	@Override
	public int getFoodPrice() {
		return 0;
	}

	@Override
	public String getFoodName() {
		return "Dish ";
	}

}
