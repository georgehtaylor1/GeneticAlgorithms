import java.awt.Dimension;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

@SuppressWarnings("serial")
public class GenerationsGraph extends ApplicationFrame {

	public GenerationsGraph(String title, ArrayList<Generation> generations) {
		super(title);
		JFreeChart lineChart = ChartFactory.createLineChart("Creature Progression", "Generation No.", "Score",
				createDataSet(generations), PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new Dimension(560, 367));
		setContentPane(chartPanel);
	}

	private CategoryDataset createDataSet(ArrayList<Generation> generations) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int i = 0;
		for(Generation g : generations){
			dataset.addValue(g.getMeanScore(), "Mean", ""+i);
			dataset.addValue(g.getMinScore(), "Max", ""+i);
			dataset.addValue(g.getMaxScore(), "Min", ""+i);
			i++;
		}
		return dataset;
	}

}
