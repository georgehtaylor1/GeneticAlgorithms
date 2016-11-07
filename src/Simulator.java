import java.awt.Canvas;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("serial")
public class Simulator extends Canvas implements Runnable{

	private ParameterSet params;
	private Random rand;
	
	private ArrayList<Creature> creatures;
	private ArrayList<Food> food;
	
	/**
	 * Default constructor for a simulator
	 * @param params The parameter set of the simulator
	 */
	public Simulator(ParameterSet params){
		this.params = params;
		rand = new Random(1); // Use a seed to make it deterministic
		creatures = new ArrayList<Creature>();
		food = new ArrayList<Food>();
	}
	
	@Override
	public void run() {
		
		
		
	}

	public ParameterSet getParams() {
		return params;
	}

	public void setParams(ParameterSet params) {
		this.params = params;
	}

}
