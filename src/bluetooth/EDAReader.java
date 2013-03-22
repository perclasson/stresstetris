package bluetooth;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
							if (isStabilized()) {
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
				break;
			}
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		spHandler.closeConnection();

	}

	public boolean getStabilized() {
		return stabilized;
	}

	private boolean isStabilized() {
		int noGSR = GSRStamps.size();
		int noValuesPerSum = 20;
		if (noGSR > 2 * noValuesPerSum) {
			List<Float> data = GSRStamps.subList(noGSR - 2 * noValuesPerSum,
					noGSR);
			float min = 0f;
			float max = 0f;
			for (float x : data) {
				max = Math.max(max, x);
				min = Math.min(min, x);
			}
			if (Math.abs(max - min) < 5) {
				return true;
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

	public Float getMedian() {
		List<Float> data = new ArrayList<Float>(GSRStamps);
		Collections.sort(data);
		if (data.isEmpty()) {
			return null;
		} else {
			return data.get(GSRStamps.size() / 2);
		}
	}

}
