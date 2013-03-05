package tetris;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class Test extends JFrame {

	private static final long serialVersionUID = 1L;
	private BufferedReader in;
	
	public void reveal() {
		pack();
		setVisible(true);
	}

	public Test(File file) {
		super("Difficulty chart");
		try {
			in = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
		String line = "";
		String[] values;
		try {
			line = in.readLine();
			while(line != null) {
				values = line.split(",");
				result.addValue(Double.parseDouble(values[1]), series1, values[0]);
				line = in.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
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
