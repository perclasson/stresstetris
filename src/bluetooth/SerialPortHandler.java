package bluetooth;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class SerialPortHandler {
	public SerialPort serialPort;
	public OutputStream outStream;
	public InputStream inStream;
	
	public BufferedReader openConnection() {
		try {
			CommPortIdentifier portId = CommPortIdentifier
					.getPortIdentifier("/dev/ttyS32");

			serialPort = (SerialPort) portId.open("Demo application", 5000);

			outStream = serialPort.getOutputStream();
			inStream = serialPort.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inStream));
			return reader;

		} catch (NoSuchPortException e) {
			e.printStackTrace();
		} catch (PortInUseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void closeConnection() {
		serialPort.close();
	}
	
	public InputStream getSerialInputStream() {
		return inStream;
	}

	public OutputStream getSerialOutputStream() {
		return outStream;
	}
}