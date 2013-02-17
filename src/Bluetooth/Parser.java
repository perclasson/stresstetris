package Bluetooth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Parser {
	public List<Float> parseInputStream(InputStream stream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		List<Float> list = new ArrayList<Float>();
		
		String line = reader.readLine();
		
		while (line != null) {
			list.add(getData(line));
		}
		
		return list;
	}
	
	public Float getData(String line) {
		return Float.parseFloat(line.split(",")[6]);		
	}
}
