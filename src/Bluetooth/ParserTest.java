package Bluetooth;

import junit.framework.TestCase;

public class ParserTest extends TestCase {
	private String line;
	private Parser parser;

	@Override
	public void setUp() {
		line = "0,0.39,0.12,0.86,3.55,32.9,0.068\r\n";
		parser = new Parser();
	}

	@Override
	public void tearDown() {
		line = null;
	}

	public void testGetData() {
		assertEquals(parser.getData(line), (float) 0.068);
	}

}
