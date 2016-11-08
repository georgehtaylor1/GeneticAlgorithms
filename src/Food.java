import java.awt.Graphics;

public class Food extends Entity {

	public Food(ParameterSet params) {
		super(params);
		setPos(Utils.getRandomPoint(params.getWindow_width(), params.getWindow_height()));
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Colors.FOOD);
		g.fillOval((int) getPos().getX() - getParams().getFood_size() / 2,
				(int) getPos().getY() - getParams().getFood_size() / 2, (int) getParams().getFood_size(),
				(int) getParams().getFood_size());
	}

	/**
	 * Eat the food (move the food to a random position)
	 */
	public void eat() {
		setPos(Utils.getRandomPoint(getParams().getWindow_width(), getParams().getWindow_height()));
	}

}
