package analyzation;

import java.util.ArrayList;

public class RegressionAnalysist {

	public double correlationAnalysisEDA(ArrayList<Float> xValues, ArrayList<Float> yValues) {
		if(xValues.size() != yValues.size()) {
			return -2.0;
		}
		double xMean = 0;
		double yMean = 0;
		double r = 0;
		for(int i = 0; i < xValues.size(); i++) {
			xMean += xValues.get(i);
			yMean += yValues.get(i);
		}
		yMean = yMean/yValues.size();
		xMean = xMean/xValues.size();

		double corrSum = 0;
		double xSum = 0;
		double ySum = 0;
		for(int i = 0; i< xValues.size(); i++) {
			corrSum += (xValues.get(i)-xMean)*(yValues.get(i)-yMean);
			xSum += Math.pow((xValues.get(i)-xMean),2);
			ySum += Math.pow((yValues.get(i)-yMean),2);
		}

		r = corrSum/(Math.sqrt(xSum)*Math.sqrt(ySum));
		return r;
	}
}
