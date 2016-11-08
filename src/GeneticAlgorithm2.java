import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GeneticAlgorithm2 {

	public static void main(String[] args) {

		ParameterSet pset = new ParameterSet(600, 600, 10, 5, 10, 10, 2, 2, 32, 45, 50);

		Simulator sim = new Simulator(pset);
		JFrame frame = new JFrame("GeneticAlgorithms2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = (JPanel) frame.getContentPane();
		panel.setPreferredSize(new Dimension(pset.getWindow_width(), pset.getWindow_height()));
		panel.setLayout(null);

		sim.setBounds(0, 0, pset.getWindow_width(), pset.getWindow_height());
		sim.setIgnoreRepaint(true);

		panel.add(sim);

		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		sim.createBufferStrategy(2);
		new Thread(sim).start();

	}

}
