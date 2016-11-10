import java.util.ArrayList;

public class Generation {

	private ArrayList<Creature> creatures;
	private double meanScore = -1;

	public Generation(ArrayList<Creature> creatures) {
		this.creatures = creatures;
	}

	/**
	 * Get the minimum score
	 * 
	 * @return The minimum score
	 */
	public int getMinScore() {
		return creatures.get(creatures.size() - 1).getScore();
	}

	/**
	 * Get the max store across the creatures
	 * 
	 * @return The max score
	 */
	public int getMaxScore() {
		return creatures.get(0).getScore();
	}

	/**
	 * Lazy implementation for the mean
	 * 
	 * @return The mean score
	 */
	public double getMeanScore() {
		if (meanScore == -1) {
			int s = 0;
			for (Creature c : creatures) {
				s += c.getScore();
			}
			meanScore = s / creatures.size();
		}
		return meanScore;
	}

}
