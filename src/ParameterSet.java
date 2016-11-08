
public class ParameterSet {

	private int window_width;
	private int window_height;

	private int creature_size;
	private int food_size;

	private int creature_count;
	private int food_count;

	private int creature_speed;
	private int creature_rotationSpeed;
	private int state_count;

	private int view_angle;
	private int view_range;

	private int generation_length;

	public ParameterSet(int window_width, int window_height, int creature_size, int food_size, int creature_count,
			int food_count, int creature_speed, int creature_rotationSpeed, int state_count, int view_angle,
			int view_range) {
		this.window_width = window_width;
		this.window_height = window_height;
		this.creature_size = creature_size;
		this.food_size = food_size;
		this.creature_count = creature_count;
		this.food_count = food_count;
		this.creature_speed = creature_speed;
		this.creature_rotationSpeed = creature_rotationSpeed;
		this.state_count = state_count;
		this.view_angle = view_angle;
		this.view_range = view_range;
	}

	public int getWindow_width() {
		return window_width;
	}

	public void setWindow_width(int window_width) {
		this.window_width = window_width;
	}

	public int getWindow_height() {
		return window_height;
	}

	public void setWindow_height(int window_height) {
		this.window_height = window_height;
	}

	public int getCreature_size() {
		return creature_size;
	}

	public void setCreature_size(int creature_size) {
		this.creature_size = creature_size;
	}

	public int getFood_size() {
		return food_size;
	}

	public void setFood_size(int food_size) {
		this.food_size = food_size;
	}

	public int getCreature_count() {
		return creature_count;
	}

	public void setCreature_count(int creature_count) {
		this.creature_count = creature_count;
	}

	public int getFood_count() {
		return food_count;
	}

	public void setFood_count(int food_count) {
		this.food_count = food_count;
	}

	public int getCreature_speed() {
		return creature_speed;
	}

	public void setCreature_speed(int creature_speed) {
		this.creature_speed = creature_speed;
	}

	public int getCreature_rotationSpeed() {
		return creature_rotationSpeed;
	}

	public void setCreature_rotationSpeed(int creature_rotationSpeed) {
		this.creature_rotationSpeed = creature_rotationSpeed;
	}

	public int getView_angle() {
		return view_angle;
	}

	public void setView_angle(int view_angle) {
		this.view_angle = view_angle;
	}

	public int getView_range() {
		return view_range;
	}

	public void setView_range(int view_range) {
		this.view_range = view_range;
	}

	public int getGeneration_length() {
		return generation_length;
	}

	public void setGeneration_length(int generation_length) {
		this.generation_length = generation_length;
	}

	public int getState_count() {
		return state_count;
	}

	public void setState_count(int state_count) {
		this.state_count = state_count;
	}

}
