package Bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class ParserTest extends TestCase {
	private String line;
	private Parser parser;
	private Dummy dummy;

	@Override
	public void setUp() {
		line = "0,0.39,0.12,0.86,3.55,32.9,0.068\r\n";
		parser = new Parser();
		dummy = new Dummy();
	}

	@Override
	public void tearDown() {
		line = null;
		parser = null;
		dummy = null;
	}

	public void testGetData() {
		assertEquals(parser.getData(line), 0.068f);
	}

	public void testParseData() {
		InputStream input = dummy.openInputStream();
		float[] correct = {0.068f, 0.071f, 0.071f, 0.071f, 0.071f, 0.071f,
				0.068f, 0.068f, 0.071f, 0.074f, 0.071f, 0.068f};
		List<Float> list = parser.parseInputStream(input);
		
		for (int i = 0; i < correct.length; i++) {
			float c = correct[i];
			float l = list.get(i);
			
			assertEquals(l, c);
		}

	}

}
