import java.io.*;
import javax.bluetooth.ServiceRecord;
import javax.microedition.io.Connection;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class RFCOMMClient {

	public static void main(String args[]) {
		try {
			StreamConnection conn = (StreamConnection) Connector.open(
			 "btgoep://0016D49A90F3:3", Connector.READ, true);
		
			
			InputStream is = conn.openInputStream();

			byte buffer[] = new byte[8];
			try {
				int bytes_read = is.read(buffer, 0, 8);
				String received = new String(buffer, 0, bytes_read);
				System.out.println("received: " + received);
			} catch (IOException e) {
				System.out.println(" FAIL");
				System.err.print(e.toString());
			}
			conn.close();
		} catch (IOException e) {
			System.err.print(e.toString());
		}
	}
}