package tetris;

import java.util.ArrayList;

import junit.framework.TestCase;

public class CorrelationTest extends TestCase {
	
	RegressionAnalysist analysist;
	
	@Override
	public void setUp() {
		analysist = new RegressionAnalysist();
	}

	@Override
	public void tearDown() {

	}

	public void testWrongData() {
		ArrayList<Double> t1 = new ArrayList<Double>();
		ArrayList<Double> t2 = new ArrayList<Double>();
		t1.add(9.0);
		t2.add(4.9);
		t1.add(43.0);
		assertEquals(-2.0, analysist.correlationAnalysis(t1, t2), 0.001);

	}

	public void testPerfectPositive() {
		ArrayList<Double> t1 = new ArrayList<Double>();
		ArrayList<Double> t2 = new ArrayList<Double>();
		t1.add(0.0);
		t2.add(0.0);
		t1.add(1.0);
		t2.add(2.0);
		t1.add(2.0);
		t2.add(4.0);
		t1.add(3.0);
		t2.add(6.0);
		t1.add(4.0);
		t2.add(8.0);
		t1.add(5.0);
		t2.add(10.0);
		assertEquals(1.0, analysist.correlationAnalysis(t1, t2), 0.001);
		System.out.println(analysist.correlationAnalysis(t1, t2));
	}
	
	public void testPerfectNegative() {
		ArrayList<Double> t1 = new ArrayList<Double>();
		ArrayList<Double> t2 = new ArrayList<Double>();
		t1.add(0.0);
		t2.add(10.0);
		t1.add(1.0);
		t2.add(8.0);
		t1.add(2.0);
		t2.add(6.0);
		t1.add(3.0);
		t2.add(4.0);
		t1.add(4.0);
		t2.add(2.0);
		t1.add(5.0);
		t2.add(0.0);		
		assertEquals(-1.0, analysist.correlationAnalysis(t1, t2), 0.001);
	}
	
	public void testExample1() {

		ArrayList<Double> t1 = new ArrayList<Double>();
		ArrayList<Double> t2 = new ArrayList<Double>();
		t1.add(176.0);
		t2.add(68.0);
		t1.add(183.0);
		t2.add(75.0);
		t1.add(179.0);
		t2.add(67.0);
		t1.add(190.0);
		t2.add(84.0);
		t1.add(184.0);
		t2.add(72.0);
		t1.add(171.0);
		t2.add(69.0);
		assertEquals(0.831, analysist.correlationAnalysis(t1, t2), 0.001);
	}

}
