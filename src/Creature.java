import java.awt.Graphics;
import java.awt.geom.Point2D;

public class Creature extends Entity {

	private double angle;

	public static int PERCEPT_COUNT = 4;
	public static int ACTION_COUNT = 3;

	private int[][] genes;
	private int maxGene = 0;

	public static int FOOD_LEFT = 0;
	public static int FOOD_RIGHT = 1;
	public static int FOOD_NONE = 2;
	public static int FOOD_STRAIGHT = 3;

	private int score = 0;

	public Creature(ParameterSet params) {
		super(params);
		maxGene = (params.getState_count() * ACTION_COUNT) - 1;
		genes = new int[params.getState_count()][PERCEPT_COUNT];
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {

		g.setColor(Colors.CREATURE);
		g.drawOval((int) getPos().getX() - getSize() / 2, (int) getPos().getY() - getSize() / 2, getSize(), getSize());
		int dx = (int) ((getSize() / 2 * Math.cos(Math.toRadians(angle))) + getPos().getX());
		int dy = (int) ((getSize() / 2 * Math.sin(Math.toRadians(angle))) + getPos().getY());
		g.drawLine((int) getPos().getX(), (int) getPos().getY(), dx, dy);

		for (Food f : Simulator.food) {
			if (Utils.getDistance(getPos(), f.getPos()) < getParams().getView_range()) {
				g.setColor(Colors.IN_RANGE);
				double foodAngle = getFoodAngle(f);
				if (foodAngle > -getParams().getView_angle() && foodAngle < 0) {
					g.setColor(Colors.LEFT_VIEW);
				}
				if (foodAngle < getParams().getView_angle() && foodAngle > 0) {
					g.setColor(Colors.RIGHT_VIEW);
				}
				if (foodAngle == 0) {
					g.setColor(Colors.DIRECT_VIEW);
				}
				g.drawLine((int) getPos().getX(), (int) getPos().getY(), (int) f.getPos().getX(),
						(int) f.getPos().getY());
			}
		}

	}

	/**
	 * Get the percept for the creature
	 * 
	 * @return The percept for the creature
	 */
	private int getPercept() {
		Food closest = getClosest();
		double distance = Utils.getDistance(getPos(), closest.getPos());
		if (distance > getParams().getView_range()) {
			return FOOD_NONE;
		}

		double foodAngle = getFoodAngle(closest);

		if (foodAngle > -getParams().getView_range() && foodAngle < 0) {
			return FOOD_LEFT;
		}
		if (foodAngle < getParams().getView_range() && foodAngle > 0) {
			return FOOD_RIGHT;
		}
		if (foodAngle == 0) {
			return FOOD_STRAIGHT;
		}
		return FOOD_NONE;
	}

	/**
	 * Get the closest item of food to the creature
	 * 
	 * @return The closest item of food
	 */
	private Food getClosest() {
		Food closest = Simulator.food.get(0);
		double distance = Utils.getDistance(closest.getPos(), getPos());
		for (Food f : Simulator.food) {
			double c = Utils.getDistance(f.getPos(), getPos());
			if (c < distance) {
				distance = c;
				closest = f;
			}
		}
		return closest;
	}

	/**
	 * Get the angle between the creature and the item of food
	 * 
	 * @param closest
	 *            The closest item of food
	 * @return The angle between the creature and the food
	 */
	private double getFoodAngle(Food closest) {
		double dx1 = ((getSize() / 2) * Math.cos(Math.toRadians(angle)));
		double dy1 = ((getSize() / 2) * Math.sin(Math.toRadians(angle)));
		double dx2 = closest.getPos().getX() - getPos().getX();
		double dy2 = closest.getPos().getY() - getPos().getY();
		double foodAngle = Math.toDegrees(Math.atan2(dx1 * dy2 - dy1 * dx2, dx1 * dx2 + dy1 * dy2));
		return foodAngle;
	}

	/**
	 * Move the creature
	 */
	private void move() {

		double x = (getPos().getX() + (getParams().getCreature_speed() * Math.cos(Math.toRadians(angle)))
				+ getParams().getWindow_width()) % getParams().getWindow_width();
		double y = (getPos().getY() + (getParams().getCreature_speed() * Math.sin(Math.toRadians(angle)))
				+ getParams().getWindow_height()) % getParams().getWindow_height();
		getPos().setLocation(x, y);
	}

	/**
	 * Turn the creature
	 * 
	 * @param dir
	 *            True = Left, False = Right
	 */
	private void turn(boolean dir) {
		angle += dir ? getParams().getCreature_rotationSpeed() : -getParams().getCreature_rotationSpeed();

		angle = angle % 360;
		if (angle > 180) {
			angle = angle - 360;
		} else if (angle < -180) {
			angle = 360 + angle;
		}
	}

	public void act() {

	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void eat() {
		setScore(getScore() + 1);

	}

}
