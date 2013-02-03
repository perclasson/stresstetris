import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.bluetooth.DataElement;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.obex.ClientSession;
import javax.obex.HeaderSet;
import javax.obex.Operation;
import javax.obex.ResponseCodes;

public class MyDiscoveryListener implements DiscoveryListener {

	private static Object lock = new Object();
	public ArrayList<RemoteDevice> devices;

	public MyDiscoveryListener() {
		devices = new ArrayList<RemoteDevice>();
	}

	public static void main(String[] args) {

		MyDiscoveryListener listener = new MyDiscoveryListener();

		try {
			LocalDevice localDevice = LocalDevice.getLocalDevice();
			DiscoveryAgent agent = localDevice.getDiscoveryAgent();
			agent.startInquiry(DiscoveryAgent.GIAC, listener);

			try {
				synchronized (lock) {
					lock.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}

			System.out.println("Device Inquiry Completed. ");

			UUID[] uuidSet = new UUID[1];
			uuidSet[0] = new UUID(0x1105); // OBEX Object Push service

			int[] attrIDs = new int[] { 0x0100 // Service name
			};

			for (RemoteDevice device : listener.devices) {
				agent.searchServices(attrIDs, uuidSet, device, listener);

				try {
					synchronized (lock) {
						lock.wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
				}

				System.out.println("Service search finished.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deviceDiscovered(RemoteDevice btDevice, DeviceClass arg1) {
		String name;
		try {
			name = btDevice.getFriendlyName(false);
		} catch (Exception e) {
			name = btDevice.getBluetoothAddress();
		}

		devices.add(btDevice);
		System.out.println("device found: " + name);

	}

	@Override
	public void inquiryCompleted(int arg0) {
		synchronized (lock) {
			lock.notify();
		}
	}

	@Override
	public void serviceSearchCompleted(int arg0, int arg1) {
		synchronized (lock) {
			lock.notify();
		}
	}

	@Override
	public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
		for (int i = 0; i < servRecord.length; i++) {
			String url = servRecord[i].getConnectionURL(
					ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
			if (url == null) {
				continue;
			}
			DataElement serviceName = servRecord[i].getAttributeValue(0x0100);
			if (serviceName != null) {
				System.out.println("service " + serviceName.getValue()
						+ " found " + url);

				if (serviceName.getValue().equals("Object Push")) {
					System.out.println("We will now try to send a message");
					sendMessageToDevice(url);
					System.out.println("We will now try to recieve a message");
					recieveMessageFromDevice(url);
				}
			} else {
				System.out.println("service found " + url);
				System.out.println("We will now try to send a message");
				sendMessageToDevice(url);
				System.out.println("We will now try to recieve a message");
				recieveMessageFromDevice(url);
			}

		}
	}
	
	public static void recieveMessageFromDevice(String serverURL) {
		try {
			System.out.println("Connecting to " + serverURL);

			ClientSession clientSession = (ClientSession) Connector
					.open(serverURL);
			HeaderSet hsConnectReply = clientSession.connect(null);
			if (hsConnectReply.getResponseCode() != ResponseCodes.OBEX_HTTP_OK) {
				System.out.println("Failed to connect");
				return;
			}
			HeaderSet hsOperation = clientSession.createHeaderSet();
			hsOperation.setHeader(HeaderSet.NAME, "response.txt");
			hsOperation.setHeader(HeaderSet.TYPE, "text");

			// Create GET Operation
			Operation getOperation = clientSession.get(hsOperation);
			// Sending the message
			//byte[] data = new byte[100];
			DataInputStream is = new DataInputStream(getOperation.openInputStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			
		    int data = is.read();
		        while (data != -1) {
		                out.write((byte) data);
		                data = is.read();
		        }
			
	        // End the transaction
	        is.close();
	        getOperation.close();
	 
	        byte[] obj = out.toByteArray();
	        out.close();
			System.out.println("We recieved stuff");
			System.out.println(new String(obj, "ISO-8859-1"));
			getOperation.close();
			clientSession.disconnect(null);
			clientSession.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	private static void sendMessageToDevice(String serverURL) {
		try {
			System.out.println("Connecting to " + serverURL);

			ClientSession clientSession = (ClientSession) Connector
					.open(serverURL);
			HeaderSet hsConnectReply = clientSession.connect(null);
			if (hsConnectReply.getResponseCode() != ResponseCodes.OBEX_HTTP_OK) {
				System.out.println("Failed to connect");
				return;
			}

			HeaderSet hsOperation = clientSession.createHeaderSet();
			hsOperation.setHeader(HeaderSet.NAME, "Hello.txt");
			hsOperation.setHeader(HeaderSet.TYPE, "text");

			// Create PUT Operation
			Operation putOperation = clientSession.put(hsOperation);
			// Sending the message
			byte data[] = "Hello World !!!".getBytes("iso-8859-1");
			System.out.println("We sent stuff");
			OutputStream os = putOperation.openOutputStream();
			os.write(data);
			os.close();
			putOperation.close();
			clientSession.disconnect(null);
			clientSession.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}