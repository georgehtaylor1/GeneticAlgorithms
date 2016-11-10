import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import javax.swing.JFrame;

import org.jfree.ui.RefineryUtilities;

@SuppressWarnings("serial")
public class Simulator extends Canvas implements Runnable, KeyListener {

	private ParameterSet params;

	private ArrayList<Creature> creatures;
	public static ArrayList<Food> food;

	private int desiredFPS = 32;
	private int desiredDeltaLoop = (1000 * 1000 * 1000) / desiredFPS;

	private int currFrame = 0;
	private int currRound = 0;

	private ArrayList<Generation> generations;

	private boolean speedRun = false;
	private boolean paused;

	/**
	 * Default constructor for a simulator
	 * 
	 * @param params
	 *            The parameter set of the simulator
	 */
	public Simulator(ParameterSet params) {
		generations = new ArrayList<Generation>();
		addKeyListener(this);
		this.params = params;
		Utils.rand = new Random(1); // Use a seed to make it deterministic
		setCreatures(new ArrayList<Creature>());
		food = new ArrayList<Food>();
		initEntities();
	}

	/**
	 * Initialize the set of food and creatures
	 */
	private void initEntities() {
		for (int i = 0; i < getParams().getCreature_count(); i++) {
			creatures.add(new Creature(getParams()));
		}
		for (int i = 0; i < getParams().getFood_count(); i++) {
			food.add(new Food(getParams()));
		}
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
			newGeneration();
			currFrame = 0;
			currRound++;
			render();
		}

	}

	private void newGeneration() {

		creatures.sort(new Comparator<Creature>() {
			public int compare(Creature c1, Creature c2) {
				return c1.getScore() == c2.getScore() ? 0 : (c1.getScore() < c2.getScore() ? 1 : -1);
			}
		});

		generations.add(new Generation(creatures));

		ArrayList<Creature> newCreatures = new ArrayList<Creature>();
		for (int i = 0; i < params.getCreature_count(); i++) {
			int j = Utils.rand.nextInt(params.getState_count());

			int p1s = 0;
			int p2s = 0;
			while (p1s < params.getCreature_count() - 1 && Utils.rand.nextDouble() > params.getSelection_probability())
				p1s++;
			while (p2s < params.getCreature_count() - 1 && Utils.rand.nextDouble() > params.getSelection_probability())
				p2s++;

			Creature p1 = creatures.get(p1s);
			Creature p2 = creatures.get(p2s);

			Creature c = new Creature(params);
			int[][] newGenes = new int[params.getState_count()][Creature.PERCEPT_COUNT];
			for (int k = 0; k < params.getState_count(); k++) {
				newGenes[k] = (k <= j) ? p1.getGenes()[k] : p2.getGenes()[k];
			}
			c.setGenes(newGenes);
			c.mutate(currRound);
			newCreatures.add(c);
		}
		creatures = newCreatures;

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

			if (!speedRun)
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

			if (speedRun || deltaLoop > desiredDeltaLoop) {
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

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Colors.BACKGROUND);
		g2.fillRect(0, 0, params.getWindow_width(), params.getWindow_height());

		for (Creature c : getCreatures()) {
			c.draw(g2);
		}

		for (Food f : food) {
			f.draw(g2);
		}

		g2.setColor(Colors.TEXT);
		g.drawString(Integer.toString(currRound) + " - " + Integer.toString(currFrame), 10, 10);
		g.drawString("Alpha: " + Double.toString(Utils.learnFunction(currRound, params.getLearning_stretch())), 10, 25);
		g.dispose();
		getBufferStrategy().show();

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

	@Override
	public void keyPressed(KeyEvent arg0) {
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_C:
			speedRun = !speedRun;
			break;
		case KeyEvent.VK_SPACE:
			paused = !paused;
			break;
		case KeyEvent.VK_G:
			graph();
			break;
		}
	}

	private void graph() {
		GenerationsGraph g = new GenerationsGraph("Generation Scores", generations);
		g.pack();
		RefineryUtilities.centerFrameOnScreen(g);
		g.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		g.setVisible(true);
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
