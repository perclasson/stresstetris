package analyzation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math.stat.regression.SimpleRegression;

public class PatternTestFile {
	private File edaFile, difficultyFile;
	private double[] difficulty, eda, rawEda;
	private double correlation, rawCorrelation;

	public PatternTestFile(File edaFile, File timeFile) {
		this.edaFile = edaFile;
		this.difficultyFile = timeFile;
		difficulty = calculateDifficulty();
		rawEda = new double[difficulty.length];
		eda = calculateEda(difficulty.length);
		correlation = new PearsonsCorrelation().correlation(difficulty, eda);
		rawCorrelation = new PearsonsCorrelation().correlation(difficulty,
				rawEda);
	}

	public String getCorrelation() {
		return String.format("%.3g", correlation);
	}

	public String getRawCorrelation() {
		return String.format("%.3g", rawCorrelation);
	}

	public String getName() {
		return edaFile.getName();
	}

	private double[] calculateDifficulty() {
		List<Double> difficulty = new ArrayList<Double>();

		try {
			BufferedReader in = new BufferedReader(new FileReader(
					difficultyFile));
			String line = in.readLine();
			while (line != null) {
				difficulty.add(Double.parseDouble(line.split(",")[0]));
				line = in.readLine();
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return toDoubleArray(difficulty);

	}

	private double[] calculateEda(int length) {
		List<SimpleRegression> regressions = new ArrayList<SimpleRegression>();
		int regressionIndex = 0;
		double firstValue = 0;

		try {
			BufferedReader in = new BufferedReader(new FileReader(edaFile));
			firstValue = readBaseline(in);
			rawEda[regressionIndex] = firstValue;
			regressions.add(new SimpleRegression());
			String line = in.readLine();
			while (line != null) {
				String[] data = line.split(",");
				double time = Double.parseDouble(data[1]);
				double eda = Double.parseDouble(data[0]);
				if ((int) time / 5000 > regressionIndex) {
					regressionIndex++;
					if (regressionIndex < length) {
						rawEda[regressionIndex] = eda;
					}
					regressions.add(new SimpleRegression());
				}
				regressions.get(regressionIndex).addData(time, eda);
				line = in.readLine();
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<Double> eda = new ArrayList<Double>();

		eda.add(firstValue);

		int i = 1;
		for (SimpleRegression regression : regressions) {
			if (i == length) {
				break;
			} else {
				eda.add(regression.predict(i * 5000));
			}
			i++;
		}

		return toDoubleArray(eda);
	}

	private double readBaseline(BufferedReader in) throws IOException {
		String line = in.readLine();
		double lastTime = 0;
		double time = 0;
		double eda = 0;
		while (line != null) {
			eda = Double.parseDouble(line.split(",")[0]);
			time = Double.parseDouble(line.split(",")[1]);
			if (time < lastTime) {
				break;
			}
			lastTime = time;
			line = in.readLine();
		}
		return eda;
	}

	private double[] toDoubleArray(List<Double> list) {
		double[] result = new double[list.size()];
		for (int i = 0; i < list.size(); i++) {
			result[i] = list.get(i);

		}
		return result;
	}
}
