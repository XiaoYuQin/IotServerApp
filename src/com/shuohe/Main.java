package com.shuohe;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.shuohe.GuoNengBattery.DataSpider;
import com.shuohe.GuoNengBattery.GuonengSpider;
import com.shuohe.GuoNengBattery.LoginSpider;
import com.shuohe.ServerMonitor.ServerMonitor;

public class Main {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
										
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		
		Debug.i("init GuonengSpider");
		GuonengSpider guonengSpider = new GuonengSpider();
		guonengSpider.start();
		
		
		ServerMonitor serverMonitor = new ServerMonitor();
		serverMonitor.start();
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	

}
