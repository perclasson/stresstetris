package analyzation;

import java.util.List;

import org.lwjgl.Sys;

public class FeedbackTest {
	private List<EdaTest> feedbackTests;
	private List<EdaTest> patternTests;

	public FeedbackTest(List<EdaTest> feedbackTests, List<EdaTest> patternTests) {
		this.feedbackTests = feedbackTests;
		this.patternTests = patternTests;
	}

	private double calculateMean(List<EdaTest> tests) {
		double sum = 0;
		double i = 0;

		for (EdaTest test : tests) {
			sum += test.getMeanEda();
			i++;
		}

		return sum / i;
	}

	public int getFeedbackSize() {
		return feedbackTests.size();
	}

	public void printDifference() {
		if (getFeedbackSize() >= 2) {
			double firstMean = feedbackTests.get(0).getMeanEda();
			double secondMean = feedbackTests.get(1).getMeanEda();

			System.out.println(firstMean);
			System.out.println(secondMean);
			System.out.println(firstMean - secondMean > 0);
			System.out.println("------");
		}
	}

	public void printBaselineDifference() {
		int i = 1;
		EdaTest test = feedbackTests.get(0);
		double baseline = test.getMeanBaseline();
		double mean = test.getMeanEda();
		i++;
		System.out.println(baseline);
		System.out.println(mean);
		System.out.println(mean - baseline > 0 ? "Medelvärde ökat"
				: "Medelvärde minskat");
		System.out.println("------");

	}
}
