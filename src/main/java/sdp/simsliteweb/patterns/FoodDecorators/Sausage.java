package sdp.simsliteweb.patterns.FoodDecorators;

public class Sausage extends FoodDecorator {

	public Sausage(Food food) {
		super(food);
	}

	@Override
	public int getFoodPrice() {
		return super.getFoodPrice() + 250;
	}

	@Override
	public String getFoodName() {
		return super.getFoodName() + "with Sausage ";
	}

}
