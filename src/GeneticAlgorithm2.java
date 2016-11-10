import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GeneticAlgorithm2 {

	public static void main(String[] args) {

		// Not too many creatures and quite large
		ParameterSet pset1 = new ParameterSet(1400, 1000, 20, 10, 20, 70, 2, 2, 32, 45, 200, 800, 200.0, 0.8, 19);

		// Lots of creatures, more breeding, longer running, everything smaller
		ParameterSet pset2 = new ParameterSet(1600, 1000, 10, 5, 50, 150, 3, 3, 48, 30, 150, 1000, 500.0, 0.7, 25);
		
		// More Food, longer burn down for alpha, slightly shorter view range!
		ParameterSet pset3 = new ParameterSet(1600, 1000, 10, 5, 50, 400, 3, 3, 48, 30, 100, 1000, 100.0, 0.8, 25);

		ParameterSet active_set = pset3;
		Simulator sim = new Simulator(active_set);
		JFrame frame = new JFrame("GeneticAlgorithms2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = (JPanel) frame.getContentPane();
		panel.setPreferredSize(new Dimension(active_set.getWindow_width(), active_set.getWindow_height()));
		panel.setLayout(null);

		sim.setBounds(0, 0, active_set.getWindow_width(), active_set.getWindow_height());
		sim.setIgnoreRepaint(true);

		panel.add(sim);

		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);

		sim.createBufferStrategy(2);
		new Thread(sim).start();

	}

}
