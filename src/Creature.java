import java.awt.Graphics;

public class Creature extends Entity implements Cloneable {

	private double angle;

	public static int PERCEPT_COUNT = 4;
	public static int ACTION_COUNT = 3;

	private int[][] genes;
	private int maxGene = 0;

	private int state;

	public static int FOOD_LEFT = 0;
	public static int FOOD_RIGHT = 1;
	public static int FOOD_NONE = 2;
	public static int FOOD_STRAIGHT = 3;

	private int score = 0;

	public static double STRAIGHT_ANGLE = 5;

	/**
	 * Creatre a new creature with the set of parameters for the simulation
	 * 
	 * @param params
	 *            THe simulation parameters
	 */
	public Creature(ParameterSet params) {
		super(params);
		maxGene = (params.getState_count() * ACTION_COUNT) - 1;
		setGenes(new int[params.getState_count()][PERCEPT_COUNT]);

		setPos(Utils.getRandomPoint(params.getWindow_width(), params.getWindow_height()));
		for (int i = 0; i < params.getState_count(); i++) {
			for (int j = 0; j < PERCEPT_COUNT; j++) {
				getGenes()[i][j] = Utils.rand.nextInt(maxGene);
			}
		}
	}

	@Override
	public void draw(Graphics g) {

		g.setColor(Colors.CREATURE);
		g.drawOval((int) getPos().getX() - getParams().getCreature_size() / 2,
				(int) getPos().getY() - getParams().getCreature_size() / 2, getParams().getCreature_size(),
				getParams().getCreature_size());
		int dx = (int) ((getParams().getCreature_size() / 2 * Math.cos(Math.toRadians(angle))) + getPos().getX());
		int dy = (int) ((getParams().getCreature_size() / 2 * Math.sin(Math.toRadians(angle))) + getPos().getY());
		g.drawLine((int) getPos().getX(), (int) getPos().getY(), dx, dy);

		for (Food f : Simulator.food) {
			if (Utils.getDistance(getPos(), f.getPos()) < getParams().getView_range()) {
				double foodAngle = getFoodAngle(f);
				if (foodAngle > -getParams().getView_angle() && foodAngle < -STRAIGHT_ANGLE) {
					g.setColor(Colors.LEFT_VIEW);
				} else if (foodAngle < getParams().getView_angle() && foodAngle > STRAIGHT_ANGLE) {
					g.setColor(Colors.RIGHT_VIEW);
				} else if (foodAngle <= STRAIGHT_ANGLE && foodAngle >= -STRAIGHT_ANGLE) {
					g.setColor(Colors.DIRECT_VIEW);
				} else {
					g.setColor(Colors.IN_RANGE);
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

		if (foodAngle > -getParams().getView_angle() && foodAngle < -STRAIGHT_ANGLE) {
			return FOOD_LEFT;
		}
		if (foodAngle < getParams().getView_angle() && foodAngle > STRAIGHT_ANGLE) {
			return FOOD_RIGHT;
		}
		if (foodAngle <= STRAIGHT_ANGLE && foodAngle >= -STRAIGHT_ANGLE) {
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
		double dx1 = ((getParams().getCreature_size() / 2) * Math.cos(Math.toRadians(angle)));
		double dy1 = ((getParams().getCreature_size() / 2) * Math.sin(Math.toRadians(angle)));
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

	/**
	 * Perform an action for the creature from the current state and environment
	 */
	public void act() {
		int percept = getPercept();
		assert (state <= getParams().getState_count() && state >= 0);
		assert (percept <= PERCEPT_COUNT && percept >= 0);
		int gene = 0;
		try {
			gene = getGenes()[state][percept];
		} catch (Exception e) {
			System.out.println(e);
		}
		state = Math.floorDiv(gene, ACTION_COUNT);
		if (state < 0) {
			System.out.println("ERR");
		}
		int action = Math.floorMod(gene, ACTION_COUNT);
		switch (action) {
		case 0:
			move();
			break;
		case 1:
			turn(true);
			break;
		case 2:
			turn(false);
			break;
		}
	}

	/**
	 * Randomly mutate the genes
	 * 
	 * @param currRound
	 *            The current round of the simulation
	 */
	public void mutate(int currRound) {
		for (int i = 0; i < getParams().getState_count(); i++) {
			for (int j = 0; j < PERCEPT_COUNT; j++) {
				int mod = (int) Math.round(Utils.learnFunction(currRound, getParams().getLearning_stretch())
						* Utils.modificationFunction(maxGene, getParams().getLearning_exponent()));
				getGenes()[i][j] += mod;
				getGenes()[i][j] = (getGenes()[i][j] + maxGene) % maxGene;
			}
		}
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

	public Creature clone() throws CloneNotSupportedException {
		return (Creature) super.clone();
	}

	public int[][] getGenes() {
		return genes;
	}

	public void setGenes(int[][] genes) {
		this.genes = genes;
	}

}
