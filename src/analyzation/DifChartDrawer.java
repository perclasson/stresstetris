package analyzation;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import tetris.DifficultyManager;
import tetris.Game;
import bluetooth.EDAReader;

public class DifChartDrawer extends JFrame {

	private static final long serialVersionUID = 1L;
	private CategoryDataset difData;
	private JFreeChart difChart;
	private ChartPanel difChartPanel;
	private File testFile;
	private ArrayList<Long> timeStamps;
	private ArrayList<Float> difStamps;

	public DifChartDrawer(File file) {
		testFile = file;
		difData = createDiffDataset();
		difChart = createDiffChart(difData, "EDA over time");
		difChartPanel = new ChartPanel(difChart);
		difChartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		getContentPane().add(difChartPanel);
	}

	public void reveal() {
		pack();
		setTitle("Data analysis");
		setVisible(true);
	}

	private CategoryDataset createDiffDataset() {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		final String series1 = "Session";

		ArrayList<Double> timeStamps = getTime();
		ArrayList<Float> difficultyStamps = getDiff();

		DecimalFormat ds = new DecimalFormat("#.");
		for (int i = 0; i < timeStamps.size(); i++) {
			Double time = Double.valueOf(ds.format(timeStamps.get(i) / 1000));
			result.addValue(difficultyStamps.get(i), series1, time);
		}
		return result;
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
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setRangeGridlinePaint(Color.DARK_GRAY);
		return chart;
	}

	public ArrayList<Double> getTime() {
		ArrayList<Double> timeStamps = new ArrayList<Double>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(testFile));
			try {
				String line = in.readLine();
				while (line != null) {
					if (!testFile.getName().contains("csv")) {
						timeStamps.add(Double.parseDouble(line.split(" ")[3]));
					} else {
						System.out.println(line.split(",")[1]);
						timeStamps.add(Double.parseDouble(line.split(",")[1]));
					}
					line = in.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return timeStamps;
	}

	public ArrayList<Float> getDiff() {
		ArrayList<Float> diffStamps = new ArrayList<Float>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(testFile));
			try {
				String line = in.readLine();
				while (line != null) {
					if (!testFile.getName().contains("csv")) {
						diffStamps.add(Float.parseFloat(line.split(" ")[1]
								.replaceAll(",", "")));
					} else {
						System.out.println(line);
						diffStamps.add(Float.parseFloat(line.split(",")[0]));
					}
					line = in.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return diffStamps;
	}
}
