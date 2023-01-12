package sdp.simsliteweb.patterns.FoodDecorators;

public class MashedPotato extends FoodDecorator {

	public MashedPotato(Food food) {
		super(food);
	}

	@Override
	public int getFoodPrice() {
		return 200 + super.getFoodPrice();
	}

	@Override
	public String getFoodName() {
		return super.getFoodName() + "with Mashed potato ";
	}

}
