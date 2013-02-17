package Bluetooth;
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
public class SPPConnector {

	// start server
	private void startServer() throws IOException {

		// Create a UUID for SPP
		UUID uuid = new UUID("1101", true);
		// Create the servicve url
		String connectionString = "btspp://101DC0869F1E:3";
		// open server url
		StreamConnection streamConn= (StreamConnection) Connector
				.open(connectionString);

		// Wait for client connection
		/*System.out
				.println("\nServer Started. Waiting for clients to connect...");
		StreamConnection connection = streamConnNotifier.acceptAndOpen();*/

		RemoteDevice dev = RemoteDevice.getRemoteDevice(streamConn);
		System.out.println("Remote device address: "
				+ dev.getBluetoothAddress());
		System.out.println("Remote device name: " + dev.getFriendlyName(true));

		// read string from spp client
		boolean listening = true;
		InputStream inStream = streamConn.openInputStream();
		BufferedReader bReader = new BufferedReader(new InputStreamReader(
				inStream));
		while (listening) {
			String lineRead = bReader.readLine();
			if (lineRead == null) {
				listening = false;
			} else {
				System.out.println(lineRead);
			}
		}
	
		streamConn.close();

	}

	public static void main(String[] args) throws IOException {

		// display local device address and name
		LocalDevice localDevice = LocalDevice.getLocalDevice();
		System.out.println("Address: " + localDevice.getBluetoothAddress());
		System.out.println("Name: " + localDevice.getFriendlyName());

		SPPConnector connector = new SPPConnector();
		connector.startServer();

	}
}