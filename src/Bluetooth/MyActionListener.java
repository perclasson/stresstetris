package Bluetooth;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyActionListener implements ActionListener{

	private SerialPortHandler handler;
	public MyActionListener(SerialPortHandler handler) {
		this.handler = handler;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		handler.close();
	}

}
