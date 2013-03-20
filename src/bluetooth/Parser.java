package bluetooth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Parser {
	public List<Float> parseInputStream(InputStream stream) {
		List<Float> list = new ArrayList<Float>();

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream));
			String line = reader.readLine();

			while (line != null) {
				list.add(getData(line));
				line = reader.readLine();
			}
		} catch (IOException e) {
			System.err.println("Stream data incorrect!");
		}

		return list;
	}

	public Float getData(String line) {
		try {
			return Float.parseFloat(line.split(",")[6]);
		}
		catch (NumberFormatException e) {
			return null;
		}
		catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
}
