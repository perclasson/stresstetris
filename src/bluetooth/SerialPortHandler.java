package bluetooth;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

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

	/**
	 * Get the serial port input stream
	 * 
	 * @return The serial port input stream
	 */
	public InputStream getSerialInputStream() {
		return inStream;
	}

	/**
	 * Get the serial port output stream
	 * 
	 * @return The serial port output stream
	 */
	public OutputStream getSerialOutputStream() {
		return outStream;
	}

	/**
	 * Sets the serial port parameters
	 */
	@SuppressWarnings("unused")
	private void setSerialPortParameters() throws IOException {
		int baudRate = 115200;

		try {
			// Set serial port to 115200bps-8N1
			serialPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
		} catch (UnsupportedCommOperationException ex) {
			throw new IOException("Unsupported serial port parameter");
		}
	}
}