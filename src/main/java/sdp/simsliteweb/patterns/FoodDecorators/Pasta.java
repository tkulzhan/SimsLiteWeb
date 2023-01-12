package sdp.simsliteweb.patterns.FoodDecorators;

public class Pasta extends FoodDecorator {
	
	public Pasta(Food food) {
		super(food);
	}

	@Override
	public int getFoodPrice() {
		return 200 + super.getFoodPrice();
	}

	@Override
	public String getFoodName() {
		return super.getFoodName() + "with Pasta ";
	}

}
