package sdp.simsliteweb.patterns.FoodDecorators;

public class Pattie extends FoodDecorator {

	public Pattie(Food food) {
		super(food);
	}

	@Override
	public int getFoodPrice() {
		return super.getFoodPrice() + 450;
	}

	@Override
	public String getFoodName() {
		return super.getFoodName() + "with Pattie ";
	}
}
