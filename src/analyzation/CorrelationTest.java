package analyzation;

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
		ArrayList<Float> t1 = new ArrayList<Float>();
		ArrayList<Float> t2 = new ArrayList<Float>();
		t1.add(9f);
		t2.add(4.9f);
		t1.add(43f);
		assertEquals(-2.0, analysist.correlationAnalysisEDA(t1, t2), 0.001);

	}

	public void testPerfectPositive() {
		ArrayList<Float> t1 = new ArrayList<Float>();
		ArrayList<Float> t2 = new ArrayList<Float>();
		t1.add(0f);
		t2.add(0.0f);
		t1.add(1.0f);
		t2.add(2.0f);
		t1.add(2.0f);
		t2.add(4.0f);
		t1.add(3.0f);
		t2.add(6.0f);
		t1.add(4.0f);
		t2.add(8.0f);
		t1.add(5.0f);
		t2.add(10.0f);
		assertEquals(1.0, analysist.correlationAnalysisEDA(t1, t2), 0.001);
		System.out.println(analysist.correlationAnalysisEDA(t1, t2));
	}

	public void testPerfectNegative() {
		ArrayList<Float> t1 = new ArrayList<Float>();
		ArrayList<Float> t2 = new ArrayList<Float>();
		t1.add(0.0f);
		t2.add(10.0f);
		t1.add(1.0f);
		t2.add(8.0f);
		t1.add(2.0f);
		t2.add(6.0f);
		t1.add(3.0f);
		t2.add(4.0f);
		t1.add(4.0f);
		t2.add(2.0f);
		t1.add(5.0f);
		t2.add(0.0f);
		assertEquals(-1.0, analysist.correlationAnalysisEDA(t1, t2), 0.001);
	}

	public void testExample1() {

		ArrayList<Float> t1 = new ArrayList<Float>();
		ArrayList<Float> t2 = new ArrayList<Float>();
		t1.add(176.0f);
		t2.add(68.0f);
		t1.add(183.0f);
		t2.add(75.0f);
		t1.add(179.0f);
		t2.add(67.0f);
		t1.add(190.0f);
		t2.add(84.0f);
		t1.add(184.0f);
		t2.add(72.0f);
		t1.add(171.0f);
		t2.add(69.0f);
		assertEquals(0.831, analysist.correlationAnalysisEDA(t1, t2), 0.001);
	}

	public void testExample2() {

		ArrayList<Float> t1 = new ArrayList<Float>();
		ArrayList<Float> t2 = new ArrayList<Float>();
		t1.add(2.373f);
		t2.add(1.0f);
		t1.add(2.444f);
		t2.add(1.5f);
		t1.add(2.44f);
		t2.add(2.0f);
		t1.add(2.496f);
		t2.add(2.5f);
		t1.add(2.496f);
		t2.add(3.0f);
		t1.add(2.567f);
		t2.add(3.5f);
		t1.add(2.585f);
		t2.add(4.0f);
		t1.add(2.6f);
		t2.add(4.5f);
		t1.add(2.697f);
		t2.add(5.0f);
		t1.add(2.717f);
		t2.add(4.5f);
		t1.add(2.775f);
		t2.add(4.0f);
		t1.add(2.856f);
		t2.add(3.5f);
		t1.add(2.86f);
		t2.add(3.0f);
		t1.add(2.881f);
		t2.add(2.5f);
		t1.add(2.836f);
		t2.add(2.0f);
		assertEquals(0.384, analysist.correlationAnalysisEDA(t1, t2), 0.001);
	}

	public void testExample3() {
		ArrayList<Float> t1 = new ArrayList<Float>();
		ArrayList<Float> t2 = new ArrayList<Float>();
		t1.add(2.4928954f);
		t2.add(1.0f);
		t1.add(2.398885f);
		t2.add(1.5f);
		t1.add(2.5190055f);
		t2.add(2.0f);
		t1.add(2.5292158f);
		t2.add(2.5f);
		t1.add(2.5371273f);
		t2.add(3.0f);
		t1.add(2.6037383f);
		t2.add(3.5f);
		t1.add(2.6337175f);
		t2.add(4.0f);
		t1.add(2.7127478f);
		t2.add(4.5f);
		t1.add(2.7136319f);
		t2.add(5.0f);
		t1.add(2.7803595f);
		t2.add(4.5f);
		t1.add(2.8701f);
		t2.add(4.0f);
		t1.add(2.8487523f);
		t2.add(3.5f);
		t1.add(2.883734f);
		t2.add(3.0f);
		t1.add(2.8456979f);
		t2.add(2.5f);
		t1.add(2.8245707f);
		t2.add(2.0f);
		assertEquals(0.476, analysist.correlationAnalysisEDA(t1, t2), 0.001);

	}

}
