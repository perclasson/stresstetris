package bluetooth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EDAReader extends Thread {
	private SerialPortHandler spHandler;
	private BufferedReader reader;
	private static ArrayList<Long> timeStampsGSR;
	private static ArrayList<Float> GSRStamps;
	private boolean finished, stabilized, feedback;
	private Parser parser;
	private float baseline;

	public EDAReader() {
		spHandler = new SerialPortHandler();
		reader = spHandler.openConnection();
		finished = false;
		stabilized = false;
		parser = new Parser();
		timeStampsGSR = new ArrayList<Long>();
		GSRStamps = new ArrayList<Float>();
		feedback = false;
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
						if (stabilized) {
							if (feedback) {
								System.out.println("Diff: " + (baseline - data));
							}
							System.out.println("Diff2: " + (baseline - data));
							GSRStamps.add(data);
							timeStampsGSR.add(delta);
						} else {
							if (isStabilized()) {
								stabilized = true;
								timeStampsGSR = new ArrayList<Long>();
								GSRStamps = new ArrayList<Float>();
							} else {
								System.out.println("Stabilizing with EDA data");
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

	public void setFeedback(boolean feedback) {
		this.feedback = feedback;
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

	public static ArrayList<Long> getTimeStampsGSROffline() {
		ArrayList<Long> GSRTimeStamps = new ArrayList<Long>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File("tests/2013-03-22 10:40 anton (eda).txt")));
			try {
			String line = in.readLine();
			while(line != null) {
				GSRTimeStamps.add(Long.parseLong(line.split(" ")[3]));
				line = in.readLine();
			}
			} catch(IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return GSRTimeStamps;	
	}
	
	public static void setTimeStampsGSR(ArrayList<Long> timeStampsGSR) {
		EDAReader.timeStampsGSR = timeStampsGSR;
	}
	

	public static ArrayList<Float> getGSRStamps() {
		return GSRStamps;
	}
	public static ArrayList<Float> getGSRStampsOffline() {
		ArrayList<Float> GSRStamps = new ArrayList<Float>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File("tests/2013-03-22 10:40 anton (eda).txt")));
			try {
			String line = in.readLine();
			while(line != null) {
				GSRStamps.add(Float.parseFloat(line.split(" ")[1].replaceAll(",", "")));
				line = in.readLine();
			}
			} catch(IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return GSRStamps;
	}

	public static void setGSRStamps(ArrayList<Float> gSRStamps) {
		GSRStamps = gSRStamps;
	}

	public void finish() {
		finished = true;
	}

	public float getNewBaseline() {
		baseline = getMedian();
		return baseline;
	}
	public float getBaseline() {
		return baseline;
	}
	
	private Float getMedian() {
		List<Float> data = new ArrayList<Float>(GSRStamps);
		Collections.sort(data);
		if (data.isEmpty()) {
			return null;
		} else {
			return data.get(GSRStamps.size() / 2);
		}
	}

}
