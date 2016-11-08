import java.awt.Canvas;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("serial")
public class Simulator extends Canvas implements Runnable {

	private ParameterSet params;

	private ArrayList<Creature> creatures;
	public static ArrayList<Food> food;

	private int desiredFPS = 24;
	private int desiredDeltaLoop = (1000 * 1000 * 1000) / desiredFPS;
	private boolean paused;

	private int currFrame = 0;
	private int currRound = 0;

	/**
	 * Default constructor for a simulator
	 * 
	 * @param params
	 *            The parameter set of the simulator
	 */
	public Simulator(ParameterSet params) {
		this.params = params;
		Utils.rand = new Random(1); // Use a seed to make it deterministic
		setCreatures(new ArrayList<Creature>());
		food = new ArrayList<Food>();
	}

	public void update() {

		if (currFrame < params.getGeneration_length()) {

			for (Creature c : getCreatures()) {
				c.act();
			}

			int eatDistance = params.getCreature_size() / 2 + params.getFood_size() / 2;
			for (Creature c : creatures) {
				for (Food f : food) {
					if (Utils.getDistance(c.getPos(), f.getPos()) < eatDistance) {
						f.eat();
						c.eat();
					}
				}
			}

			currFrame++;

		} else {
			// TODO: End of round
			currRound++;
		}

	}

	public void render() {
		paint(getBufferStrategy().getDrawGraphics());
	}

	@Override
	public void run() {

		boolean running = true;
		long beginLoopTime;
		long endLoopTime;
		long currentUpdateTime = System.nanoTime();
		long lastUpdateTime;
		long deltaLoop;

		while (running) {
			beginLoopTime = System.nanoTime();

			render();

			lastUpdateTime = currentUpdateTime;
			currentUpdateTime = System.nanoTime();
			update();

			while (paused) {
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			endLoopTime = System.nanoTime();
			deltaLoop = endLoopTime - beginLoopTime;

			if (deltaLoop > desiredDeltaLoop) {
				// Do nothing. We are already late.
			} else {
				try {
					Thread.sleep((desiredDeltaLoop - deltaLoop) / (1000 * 1000));
				} catch (InterruptedException e) {
					// Do nothing
				}
			}
		}

	}

	public void paint(Graphics g) {

		g.setColor(Colors.BACKGROUND);
		g.clearRect(0, 0, params.getWindow_width(), params.getWindow_height());
		
		for (Creature c : getCreatures()) {
			c.draw(g);
		}

		for (Food f : food) {
			f.draw(g);
		}

	}

	public ParameterSet getParams() {
		return params;
	}

	public void setParams(ParameterSet params) {
		this.params = params;
	}

	public ArrayList<Creature> getCreatures() {
		return creatures;
	}

	public void setCreatures(ArrayList<Creature> creatures) {
		this.creatures = creatures;
	}

}
