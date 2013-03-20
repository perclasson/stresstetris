package bluetooth;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class EDAReader extends Thread {
	private SerialPortHandler spHandler;
	private BufferedReader reader;
	private static ArrayList<Long> timeStampsGSR;
	private static ArrayList<Float> GSRStamps;
	private boolean finished;
	private boolean stabilized;
	private Parser parser;

	public EDAReader() {
		spHandler = new SerialPortHandler();
		reader = spHandler.openConnection();
		finished = false;
		stabilized = false;
		parser = new Parser();
		timeStampsGSR = new ArrayList<Long>();
		GSRStamps = new ArrayList<Float>();
	}

	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		long currentTime;
		while (!finished) {
			try {
				String line = reader.readLine();
				currentTime = System.currentTimeMillis();
				long delta = currentTime - startTime;
				if (line != null) {
					Float data = parser.getData(line);
					if (data != null) {
						System.out.println("EDA: " + data + ", time stamp: "
								+ delta + ", stabilized: " + stabilized);
						if (stabilized) {
							GSRStamps.add(data);
							timeStampsGSR.add(delta);
						} else {
							if (calcStabilized()) {
								stabilized = true;
								timeStampsGSR = new ArrayList<Long>();
								GSRStamps = new ArrayList<Float>();
							} else {
								GSRStamps.add(data);
								timeStampsGSR.add(delta);
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		spHandler.closeConnection();

	}

	public boolean isStabilized() {
		return stabilized;
	}

	private boolean calcStabilized() {
		int noGSR = GSRStamps.size();
		int noValuesPerSum = 20;
		float firstSum = 0;
		float secondSum = 0;
		if (noGSR > 2 * noValuesPerSum) {
			for (int i = noGSR - 2 * noValuesPerSum; i < noGSR - noValuesPerSum; i++) {
				firstSum += GSRStamps.get(i);
				secondSum += GSRStamps.get(i + noValuesPerSum);
			}
			// The difference in average between the two sums must be less then
			// 1 for it to be stabilized
			System.out.println((Math.abs(secondSum - firstSum) > 1 * noValuesPerSum));
			if (Math.abs(secondSum - firstSum) > 1 * noValuesPerSum) {
				return false;
			}
		}
		return false;

	}

	public static ArrayList<Long> getTimeStampsGSR() {
		return timeStampsGSR;
	}

	public static void setTimeStampsGSR(ArrayList<Long> timeStampsGSR) {
		EDAReader.timeStampsGSR = timeStampsGSR;
	}

	public static ArrayList<Float> getGSRStamps() {
		return GSRStamps;
	}

	public static void setGSRStamps(ArrayList<Float> gSRStamps) {
		GSRStamps = gSRStamps;
	}

	public void finish() {
		finished = true;
	}

}
