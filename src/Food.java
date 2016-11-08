import java.awt.Graphics;

public class Food extends Entity {

	public Food(ParameterSet params) {
		super(params);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Colors.FOOD);
		g.drawOval((int) getPos().getX() - getParams().getFood_size() / 2,
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
