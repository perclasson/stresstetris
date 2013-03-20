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
	private Parser parser;

	public EDAReader() {
		spHandler = new SerialPortHandler();
		reader = spHandler.openConnection();
		finished = false;
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
					float data = parser.getData(line);
					System.out.println("EDA: " + data + ", time stamp: " + delta);
					GSRStamps.add(data);
					timeStampsGSR.add(delta);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
