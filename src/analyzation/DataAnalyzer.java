package analyzation;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import tetris.DifficultyManager;
import tetris.Game;
import tetris.GamePlayState;
import bluetooth.EDAReader;

public class DataAnalyzer extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private final long interval = 5000;

	private JTextField filename = new JTextField(), dir = new JTextField();

	private JButton showEDA = new JButton("Show EDA chart"),
			showDiff = new JButton("Show Difficulty chart");

	public DataAnalyzer() {
		JPanel p = new JPanel();
		showEDA.addActionListener(new EDAChartListener());
		p.add(showEDA);
		showDiff.addActionListener(new DiffChartListener());
		p.add(showDiff);
		Container cp = getContentPane();
		cp.add(p);
	}
	
	

	class EDAChartListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			if (new File("/home").list()[0].equals("per")) {
				fc.setCurrentDirectory(new File("/home/per/workspace/stresstetris/tests"));
			}
			else {
				fc.setCurrentDirectory(new File("/home/anton/workspace/stresstetris/tests"));
			}
			int rVal = fc.showOpenDialog(DataAnalyzer.this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				EDAChartDrawer drawer = new EDAChartDrawer(file);
				drawer.reveal();
			}
		}
	}

	class DiffChartListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(System.getProperty("user.home"))); 
			// Demonstrate "Open" dialog:
			int rVal = fc.showOpenDialog(DataAnalyzer.this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
			}
		}
	}

	public static void main(String[] args) {
		DataAnalyzer analyzer = new DataAnalyzer();
		analyzer.reveal();
	}

	public void reveal() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(250, 110);
		this.setVisible(true);
	}

	/*
	 * public DataAnalyzer() { super("Statistics"); CategoryDataset diffData =
	 * createDiffDataset(); diffChart = createDiffChart(diffData,
	 * "Difficulty over time"); ChartPanel diffChartPanel = new
	 * ChartPanel(diffChart); diffChartPanel.setPreferredSize(new
	 * java.awt.Dimension(500, 270));
	 * 
	 * edaData = createEDADataset(0); edaChart = createEDAChart(edaData,
	 * "EDA over time");
	 * 
	 * edaChartPanel = new ChartPanel(edaChart);
	 * edaChartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
	 * 
	 * getContentPane().add(diffChartPanel, BorderLayout.EAST);
	 * getContentPane().add(edaChartPanel, BorderLayout.WEST); createMenu(); }
	 */

	

	private CategoryDataset createDiffDataset() {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		final String series1 = "Session";

		ArrayList<Double> timeStamps;
		ArrayList<Float> difficultyStamps;

		timeStamps = EDAReader.getTimeStampsDiff();
		difficultyStamps = EDAReader.getDifficultyStamps();
		timeStamps = DifficultyManager.getTimeStamps();
		difficultyStamps = DifficultyManager.getDifficultyStamps();

		DecimalFormat ds = new DecimalFormat("#.");
		for (int i = 0; i < timeStamps.size(); i++) {
			Double time = Double.valueOf(ds.format(timeStamps.get(i)));
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
		return chart;
	}

	
}
