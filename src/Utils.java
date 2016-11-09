import java.awt.geom.Point2D;
import java.util.Random;

public class Utils {

	public static Random rand;

	/**
	 * Get the distance between two points
	 * 
	 * @param pos
	 *            The first point
	 * @param pos2
	 *            The second point
	 * @return The distance between the two points
	 */
	public static double getDistance(Point2D pos, Point2D pos2) {
		double dx = Math.abs(pos.getX() - pos2.getX());
		double dy = Math.abs(pos.getY() - pos2.getY());
		double dx2 = Math.pow(dx, 2);
		double dy2 = Math.pow(dy, 2);
		double d = Math.sqrt(dx2 + dy2);
		return d;
	}

	/**
	 * Return a random position
	 * 
	 * @param xLim
	 *            The upper x limit
	 * @param yLim
	 *            The upper y limit
	 * @return The random point
	 */
	public static Point2D getRandomPoint(int xLim, int yLim) {
		int x = rand.nextInt(xLim);
		int y = rand.nextInt(yLim);
		return new Point2D.Double(x, y);
	}

	public static double learnFunction(int currRound, double stretch) {
		return Math.pow(Math.E, -(currRound / stretch));
	}

	public static double modificationFunction(int exponent) {
		double r = rand.nextDouble() * 2 - 1;
		return Math.pow(r, exponent);
	}

}
