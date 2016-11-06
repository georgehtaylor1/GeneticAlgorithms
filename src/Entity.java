import java.awt.Graphics;
import java.awt.geom.Point2D;

public abstract class Entity {

	private Point2D pos;
	private int size;

	public Entity(int size){
		this.size = size;
	}
	
	/**
	 * Get the entities position
	 * @return The position of the entity
	 */
	public Point2D getPos() {
		return pos;
	}

	/**
	 * Set the position of the entity
	 * @param pos The new position of the entity
	 */
	public void setPos(Point2D pos) {
		this.pos = pos;
	}

	/**
	 * Get the size of the entity
	 * @return The entities size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Set the size of the entity
	 * @param size THe new size of the entity
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	/**
	 * Draw the entity
	 * @param g The graphics component
	 */
	public abstract void draw(Graphics g);
	
}
