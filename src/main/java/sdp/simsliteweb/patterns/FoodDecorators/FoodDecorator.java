package sdp.simsliteweb.patterns.FoodDecorators;

public abstract class FoodDecorator implements Food {
	private Food food;
	
	public FoodDecorator(Food food) {
		this.food = food;
	}

	@Override
	public int getFoodPrice() {
		return food.getFoodPrice();
	}

	@Override
	public String getFoodName() {
		return food.getFoodName();
	}

}
