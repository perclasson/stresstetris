package Bluetooth;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Dummy {
	private DataInputStream input;
	private FileInputStream fis;

	public Dummy(String filePath) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(filePath);
			input = new DataInputStream(fis);
		} catch (FileNotFoundException e) {
			System.err.println("The file \"" + filePath + "\" could not be found.");
		}
	}
	
	public Dummy() {
		String file = "log.txt";
		try {
			fis = new FileInputStream(file);
			input = new DataInputStream(fis);
		} catch (FileNotFoundException e) {
			System.err.println("The file \"" + file + "\" could not be found.");
		}
		
	}
	
	public InputStream openInputStream() {
		return input;
	}
	
	public void shutDown() {
		try {
			input.close();
			fis.close();
		} catch (IOException e) {
			System.err.println("Error while closing the inputstreams.");
		}
	}
	
 }
 
