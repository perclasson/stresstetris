package tetris;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import bluetooth.EDAReader;

public class ChartDrawer extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private CategoryDataset dataset2;
	private JFreeChart chart2;
	private ChartPanel chartPanel2, chartPanel;
	
	public void reveal() {
		pack();
		setVisible(true);
	}

	public ChartDrawer() {
		super("EDA chart");
		// This will create the dataset
		CategoryDataset dataset = createDiffDataset();
		// based on the dataset we create the chart
		JFreeChart chart = createDiffChart(dataset, "Difficulty over time");
		// we put the chart into a panel
		ChartPanel chartPanel = new ChartPanel(chart);
		// default size
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		// add it to our application
		
		dataset2 = createEDADataset(0);

		chart2 = createEDAChart(dataset2, "EDA over time");
		// we put the chart into a panel
		chartPanel2 = new ChartPanel(chart2);
		// default size
		chartPanel2.setPreferredSize(new java.awt.Dimension(500, 270));
		// add it to our application
		getContentPane().add(chartPanel, BorderLayout.EAST);
		getContentPane().add(chartPanel2, BorderLayout.WEST);
		createMenu();
	} 
	
	public void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Curve fitting");
		menuBar.add(menu);	
		JMenuItem menuItem1 = new JMenuItem("Raw");
		menuItem1.addActionListener(this);
		JMenuItem menuItem2 = new JMenuItem("Linear Least Squares");
		menuItem2.addActionListener(this);
		menu.add(menuItem1);
		menu.add(menuItem2);
		JMenu saveMenu = new JMenu("File");
		JMenuItem saveMenuItem = new JMenuItem("Save test");
		saveMenuItem.addActionListener(this);
		saveMenu.add(saveMenuItem);
		menuBar.add(saveMenu);
		setJMenuBar(menuBar);
		menuItem1.setActionCommand("RAW");
		menuItem2.setActionCommand("LLS");
		saveMenuItem.setActionCommand("SAVE");
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
	
	private ArrayList<Float> fitCurve(ArrayList<Float> edaStamps, ArrayList<Long> timeStamps) {
		ArrayList<Float> fitted = new ArrayList<Float>();
		for(int i = 0; i+4 < edaStamps.size(); i+=4) {
			long x1 = timeStamps.get(i);
			long x2 = timeStamps.get(i+1);
			long x3 = timeStamps.get(i+2);
			long x4 = timeStamps.get(i+3);
			
			float y1 = edaStamps.get(i);
			float y2 = edaStamps.get(i+1);
			float y3 = edaStamps.get(i+2);
			float y4 = edaStamps.get(i+3);
			
			float c1 = 2*(y1+y2+y3+y4); //konstanter som står framför de okända i normalekvationerna
			float c2 = 2*((x1*y1)+(x2*y2)+(x3*y3)+(x4*y4));
			float a1 = 2*4;
			float a2 = 2*(x1+x2+x3+x4);
			float b1 = 2*(x1+x2+x3+x4);
			float b2 = 2*((x1*x1)+(x2*x2)+(x3*x3)+(x4*x4));
			
			float Det11 = (c1*b2)-(b1*c2); //Vi räknar ut determinanterna för att lösa systemet
		    float Det12 = (a1*b2)-(b1*a2);

			float Det21 = (a1*c2)-(a2*c1);
			float Det22 = (a1*b2)-(a2*b1);
			
			float b1Sol = Det11/Det12; //ySol och xSol bildar ekvationen --y=xSol + ySol*x--
			float b2Sol = Det21/Det22;
			fitted.add(b1Sol+b2Sol*x4);
		}
		return fitted;
	}
	
	public ArrayList<Long> formatStamps(ArrayList<Long> timeStamps) {
		ArrayList<Long> result = new ArrayList<Long>();
		for(int i = 0; i+4 < timeStamps.size();i+=4) {
			result.add(timeStamps.get(i));
		}
		return result;
	}
	
	private CategoryDataset createEDADataset(int fittingMethod) {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		final String series1 = "Session";
		ArrayList<Long> timeStamps =EDAReader.getTimeStampsGSR();
		ArrayList<Float> gsrStamps = EDAReader.getGSRStamps();
		
		switch(fittingMethod) {
		case 1:
			System.out.println("Fitted using LSM!");
			gsrStamps = fitCurve(gsrStamps, timeStamps);
			timeStamps = formatStamps(timeStamps);
			break;
		}
		
	
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
				"EDA over time", // chart title
				"Time (seconds)", // domain axis label
				"EDA", // range axis label
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String command = arg0.getActionCommand();
		if(command.equals("LLS")) {
			getContentPane().remove(chartPanel2);
			dataset2 = createEDADataset(1);
			System.out.println("Success! :D");
			chart2 = createEDAChart(dataset2, "EDA over time");
			// we put the chart into a panel
			chartPanel2 = new ChartPanel(chart2);
			// default size
			chartPanel2.setPreferredSize(new java.awt.Dimension(500, 270));
			getContentPane().add(chartPanel2, BorderLayout.WEST);
			this.validate();
		} else if(command.equals("RAW")) {
			getContentPane().remove(chartPanel2);

			dataset2 = createEDADataset(0);
			System.out.println("Success! :D");
			chart2 = createEDAChart(dataset2, "EDA over time");
			// we put the chart into a panel
			chartPanel2 = new ChartPanel(chart2);
			// default size
			chartPanel2.setPreferredSize(new java.awt.Dimension(500, 270));
			getContentPane().add(chartPanel2, BorderLayout.WEST);
			this.validate();
		} else if(command.equals("SAVE")) {
			saveTestToFiles();
		}
	}
	
	public void saveTestToFiles() {
		ArrayList<Long> edaTimeStamps =EDAReader.getTimeStampsGSR();
		ArrayList<Float> gsrStamps = EDAReader.getGSRStamps();
		ArrayList<Double> diffTimeStamps = DifficultyManager.getTimeStamps();
		ArrayList<Float> difficultyStamps = DifficultyManager.getDifficultyStamps();
		
		PrintWriter writer;
		try {
	
		    String name = JOptionPane.showInputDialog(this,"Enter the name of the test:",null);
			writer = new PrintWriter("tests/"+name+"(eda).txt", "UTF-8");
			for(int i = 0; i < gsrStamps.size();i++) {
				writer.println("EDA: " + gsrStamps.get(i) + ", Time: " + edaTimeStamps.get(i));
			}
			writer.close();
			writer = new PrintWriter("tests/"+name+"(diff).txt", "UTF-8");
			for(int i = 0; i < diffTimeStamps.size();i++) {
				writer.println("Difficulty: " + difficultyStamps.get(i) + ", Time: " + diffTimeStamps.get(i));
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
}
