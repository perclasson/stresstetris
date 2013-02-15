
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.bluetooth.*;
import javax.microedition.io.*;

/**
 * Class that implements an SPP Server which accepts single line of message from
 * an SPP client and sends a single line of response to the client.
 */
public class SimpleSPPServer {
	public static void main(String[] args) throws IOException {
		System.out.println("Main staring");
		SimpleSPPServer server = new SimpleSPPServer();
		server.runServer();
	}

	/**
	 * runs a bluetooth client that accepts a string from a client and sends a
	 * response
	 */
	public void runServer() throws IOException {

		// display local device address and name
		LocalDevice localDevice = LocalDevice.getLocalDevice();
		System.out.println("Address: " + localDevice.getBluetoothAddress());
		System.out.println("Name: " + localDevice.getFriendlyName());

		// run server
		this.startServer();

	} // runServer

	// start server
	private void startServer() throws IOException {

		// Create a UUID
		UUID uuid = new UUID("1101", true); // serial, SPP
		// UUID uuid = new UUID("1105", true); // obex obj push
		// UUID uuid = new UUID("0003", true); // rfcomm
		// UUID uuid = new UUID("1106", true); // obex file transfer
		System.out.println(uuid.toString());
		// Create the service url
		String connectionString = "btspp://localhost:" + uuid +";name=Sample SPP Server";
        

		// open server url
		StreamConnectionNotifier streamConnNotifier = (StreamConnectionNotifier)Connector.open( connectionString );

		// Wait for client connection
		System.out
				.println("\nServer Started. Waiting for clients to connect...");
		StreamConnection connection = streamConnNotifier.acceptAndOpen();

		// connect
		System.out.println("Connecting to client...");
		RemoteDevice dev = RemoteDevice.getRemoteDevice(connection);
		try {
			System.out.println("Remote device address: "
					+ dev.getBluetoothAddress());
			System.out.println("Remote device name: "
					+ dev.getFriendlyName(true));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		// read string from spp client
		InputStream inStream = connection.openInputStream();
		BufferedReader bReader = new BufferedReader(new InputStreamReader(
				inStream));
		String lineRead = bReader.readLine();
		System.out.println("TEST" + lineRead);

		// read string from spp client
		Thread recvT = new Thread(new recvLoop(connection));
		recvT.start();

		// send response to spp client
		OutputStream outStream = connection.openOutputStream();
		PrintWriter pWriter = new PrintWriter(new OutputStreamWriter(outStream));
		pWriter.write("Response String from SPP Server\r\n");
		pWriter.flush();

		// send response to spp client
		Thread sendT = new Thread(new sendLoop(connection));
		sendT.start();

		System.out.println("\nServer threads started");

		// stay alive
		while (true) {
			try {
				Thread.sleep(2000);
				// System.out.println("\nServer looping.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	} // startServer

	private static class recvLoop implements Runnable {
		private StreamConnection connection = null;
		private InputStream inStream = null;

		public recvLoop(StreamConnection c) {
			this.connection = c;
			try {
				this.inStream = this.connection.openInputStream();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			while (true) {
				try {

					BufferedReader bReader = new BufferedReader(
							new InputStreamReader(inStream));
					String lineRead = bReader.readLine();
					System.out.println("Server recv: " + lineRead);
					Thread.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	} // recvLoop

	private static class sendLoop implements Runnable {
		private StreamConnection connection = null;
		PrintWriter pWriter = null;

		public sendLoop(StreamConnection c) {
			this.connection = c;
			OutputStream outStream;
			try {
				outStream = this.connection.openOutputStream();
				this.pWriter = new PrintWriter(
						new OutputStreamWriter(outStream));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			while (true) {
				try {
					// prompt
					System.out.println("Enter message: ");
					BufferedReader bReader = new BufferedReader(
							new InputStreamReader(System.in));
					String inputline = bReader.readLine();
					pWriter.write(inputline);
					pWriter.flush();
					System.out.println("Server send: " + inputline);
					Thread.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	} // sendLoop

} // class
