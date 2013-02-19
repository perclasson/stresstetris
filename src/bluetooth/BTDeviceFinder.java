package bluetooth;
import java.io.IOException;
import java.util.ArrayList;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;


public class BTDeviceFinder {

	private ArrayList<RemoteDevice> devicesDiscovered = new ArrayList<RemoteDevice>();

	public static void main(String[] args) {
		BTDeviceFinder finder = new BTDeviceFinder();
		finder.startSearch();
	}
	
	public void startSearch() {
		try {
			final Object inquiryCompletedEvent = new Object();
			DiscoveryListener listener = new DiscoveryListener() {

				public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
					System.out.println("Device " + btDevice.getBluetoothAddress()
							+ " found");
					devicesDiscovered.add(btDevice);
					try {
						System.out.println("     name "
								+ btDevice.getFriendlyName(false));
						System.out.println("     address "
								+ btDevice.getBluetoothAddress());
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
					try {
						inquiryCompletedEvent.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(devicesDiscovered.size()
							+ " device(s) found");
				}
			}
		} catch (BluetoothStateException e) {
			e.printStackTrace();
		}
	}
	
	
}
