package tetris;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class ChartDrawer extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public void reveal() {
		pack();
		setVisible(true);
	}

	public ChartDrawer(DifficultyManager manager) {
		super("Difficulty chart");
		// This will create the dataset
		CategoryDataset dataset = createDataset();
		// based on the dataset we create the chart
		JFreeChart chart = createChart(dataset, "Difficulty over time");
		// we put the chart into a panel
		ChartPanel chartPanel = new ChartPanel(chart);
		// default size
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		// add it to our application
		setContentPane(chartPanel);
		


	}

	/**
	 * Creates a sample dataset
	 */

	private CategoryDataset createDataset() {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		final String series1 = "Session";
		ArrayList<Double> timeStamps = DifficultyManager.getTimeStamps();
		ArrayList<Float> difficultyStamps = DifficultyManager.getDifficultyStamps();
		DecimalFormat ds = new DecimalFormat("#.");
		for(int i = 0; i < timeStamps.size(); i++) {
			Double time = Double.valueOf(ds.format(timeStamps.get(i)));
			result.addValue(difficultyStamps.get(i),series1,time);
		}
	
		return result;

	}

	/**
	 * Creates a chart
	 */

	private JFreeChart createChart(CategoryDataset dataset, String title) {

		final JFreeChart chart = ChartFactory.createLineChart(
				"Difficulty over time", // chart title
				"Time (seconds)", // domain axis label
				"Difficulty", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips
				false // urls
				);
		
		return chart;

	}
}
