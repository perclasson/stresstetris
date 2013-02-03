import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.bluetooth.*;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import javax.obex.ClientSession;
import javax.obex.HeaderSet;
import javax.obex.Operation;
import javax.obex.ResponseCodes;

public class BluetoothListener {
	private LocalDevice testDevice;
	private static final ArrayList<RemoteDevice> devicesDiscovered = new ArrayList<RemoteDevice>();
	private static Object lock = new Object();

	public BluetoothListener() {
		try {
			testDevice = LocalDevice.getLocalDevice();
		} catch (BluetoothStateException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		BluetoothListener listener = new BluetoothListener();
		try {
			listener.discoverDeviceOld();
		} catch (BluetoothStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//listener.acceptConnection();
	}
	
	public void acceptConnection() {
		try {
		// let's name our variables

		StreamConnectionNotifier notifier = null;
		StreamConnection con = null;
		LocalDevice localdevice = null;
		ServiceRecord servicerecord = null;
		InputStream input;
		OutputStream output;

		// let's create a URL that contains a UUID that 
		// has a very low chance of conflicting with anything
		String url = 
		  "btspp://localhost:00112233445566778899AABBCCDDEEFF;name=serialconn";
		// let's open the connection with the url and
		// cast it into a StreamConnectionNotifier
		notifier = (StreamConnectionNotifier)Connector.open(url);

		// block the current thread until a client responds
		con = notifier.acceptAndOpen();

		// the client has responded, so open some streams
		input = con.openInputStream();
		output = con.openOutputStream();

		// now that the streams are open, send and
		// receive some data
		}
		catch(IOException e) {
			
		}
	}

	public void discoverDeviceOld() throws BluetoothStateException,
			InterruptedException {
		final Object inquiryCompletedEvent = new Object();

		DiscoveryListener listener = new DiscoveryListener() {

			public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
				System.out.println("Device " + btDevice.getBluetoothAddress()
						+ " found");
				devicesDiscovered.add(btDevice);
				try {
					System.out.println("     name "
							+ btDevice.getFriendlyName(false));
				} catch (IOException cantGetDeviceName) {
				}
			}

			public void inquiryCompleted(int discType) {
				System.out.println("Device Inquiry completed!");
				synchronized (inquiryCompletedEvent) {
					inquiryCompletedEvent.notifyAll();
				}
			}

			public void serviceSearchCompleted(int transID, int respCode) {
			}

			public void servicesDiscovered(int transID,
					ServiceRecord[] servRecord) {
			}
		};

		synchronized (inquiryCompletedEvent) {
			boolean started = LocalDevice.getLocalDevice().getDiscoveryAgent()
					.startInquiry(DiscoveryAgent.GIAC, listener);
			if (started) {
				System.out.println("wait for device inquiry to complete...");
				inquiryCompletedEvent.wait();
				System.out.println(devicesDiscovered.size()
						+ " device(s) found");
			}
		}
		RemoteDevice myDevice = devicesDiscovered.get(0);
	
		UUID[] uuidSet = new UUID[1];
		 uuidSet[0]=new UUID(0x1105); //OBEX Object Push service

		int[] attrIDs =  new int[] {
		        0x0100 // Service name
		};
		LocalDevice localDevice = LocalDevice.getLocalDevice();
		DiscoveryAgent agent = localDevice.getDiscoveryAgent();
		 agent.searchServices(null,uuidSet,myDevice, new MyDiscoveryListener());
		 
		    try {
		        synchronized(lock){
		            lock.wait();
		        }
		    }
		    catch (InterruptedException e) {
		        e.printStackTrace();
		        return;
		    }
	}

	public void isActive() {
		System.out.println(LocalDevice.isPowerOn());
		System.out.println(testDevice.getFriendlyName());
		System.out.println(testDevice.getBluetoothAddress());
	}
}
