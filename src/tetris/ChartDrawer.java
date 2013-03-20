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

import bluetooth.EDAReader;

public class ChartDrawer extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public void reveal() {
		pack();
		setVisible(true);
	}

	public ChartDrawer(DifficultyManager manager) {
		super("Difficulty chart");
		// This will create the dataset
		CategoryDataset dataset = createDiffDataset();
		// based on the dataset we create the chart
		JFreeChart chart = createDiffChart(dataset, "Difficulty over time");
		// we put the chart into a panel
		ChartPanel chartPanel = new ChartPanel(chart);
		// default size
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		// add it to our application
		CategoryDataset dataset2 = createEDADataset();

		JFreeChart chart2 = createEDAChart(dataset2, "EDA over time");
		// we put the chart into a panel
		ChartPanel chartPanel2 = new ChartPanel(chart2);
		// default size
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		// add it to our application
		setContentPane(chartPanel);
		setContentPane(chartPanel2);
	}

	/**
	 * Creates a sample dataset
	 */

	private CategoryDataset createDiffDataset() {
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
	
	private CategoryDataset createEDADataset() {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		final String series1 = "Session";
		ArrayList<Long> timeStamps =EDAReader.getTimeStampsGSR();
		ArrayList<Float> gsrStamps = EDAReader.getGSRStamps();
		for(int i = 0; i < timeStamps.size(); i++) {
			Long time = timeStamps.get(i);
			result.addValue(gsrStamps.get(i),series1,time);
		}
	
		return result;

	}

	/**
	 * Creates a chart
	 */

	private JFreeChart createEDAChart(CategoryDataset dataset, String title) {

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
	
	private JFreeChart createDiffChart(CategoryDataset dataset, String title) {

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
