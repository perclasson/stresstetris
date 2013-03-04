package tetris;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class DifficultyManager {

	private BufferedReader in;
	private boolean running, isReady;
	private GamePlayState gps;
	private double currentTime, changeTime;
	private float difficulty;

	public DifficultyManager(File file, GamePlayState gps) {
		try {
			in = new BufferedReader(new FileReader(file));
			running = true;
			isReady = true;
			currentTime = 0;
			changeTime = 0;
			difficulty = 0;
			this.gps = gps;
		} catch (FileNotFoundException e) {
			System.err.println("Could not find file: " + file.getName());
		}
	}

	public void update(int delta) {
		String line = "";
		currentTime += delta;
		
		while (running) {
			try {
				if(isReady) {
					line = in.readLine();
					String[] args = line.split(",");
					difficulty = Float.parseFloat(args[1]);
					changeTime = Double.parseDouble(args[0]);
				}
				if(changeTime <= currentTime) {
					System.out.println("Time: " + currentTime);
					System.out.println("Difficulty: " + difficulty);
					gps.setBlockSpeed(difficulty);
					isReady = true;
				} else {
					isReady = false;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}