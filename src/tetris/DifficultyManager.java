package tetris;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import bluetooth.EDAReader;

public class DifficultyManager {

	private BufferedReader in;
	private boolean isReady, GSRFeedback;
	private GamePlayState gps;
	private double currentTime, changeTime;
	private float difficulty;
	private static ArrayList<Double> timeStampsDiff;
	private static ArrayList<Float> difficultyStamps;
	private EDAReader edaReader;

	public DifficultyManager(Game game, GamePlayState gamePlayState) {
		try {
			in = new BufferedReader(new FileReader(game.optionsFile));
			isReady = true;
			currentTime = 0;
			this.GSRFeedback = game.useGSRFeedback;
			this.edaReader = game.getEDAReader();
			changeTime = 0;
			difficulty = 0;
			timeStampsDiff = new ArrayList<Double>();
			difficultyStamps = new ArrayList<Float>();
			this.gps = gamePlayState;
		} catch (FileNotFoundException e) {
			System.err.println("Could not find file: " + game.optionsFile.getName());
		}
	}

	public void update(int delta) {
		if (!GSRFeedback) {
			updateDiffFromFile(delta);
		}
		else {
			edaReader.setFeedback(true);
		}
	}

	public void updateDiffFromFile(int delta) {
		String line = "";
		currentTime += delta;
		try {
			if (isReady) {
				line = in.readLine();
				if (line != null) {
					String[] args = line.split(",");
					difficulty = Float.parseFloat(args[1]);
					changeTime = Double.parseDouble(args[0]) * 1000;
				} else {
					return;
				}
			}
			if (changeTime <= currentTime) {
				timeStampsDiff.add(Double.valueOf(currentTime / 1000));
				difficultyStamps.add(difficulty);
				gps.updatePitch(difficulty);
				gps.setBlockSpeed(difficulty);
				isReady = true;
			} else {
				isReady = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Double> getTimeStamps() {
		return timeStampsDiff;
	}

	public static ArrayList<Float> getDifficultyStamps() {
		return difficultyStamps;
	}
}