package com.epiphan.vga2usb;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.AccessDeniedException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;


public class FrameGrabbedToServer {

	public static void main(String[] args){
		try {
			Grabber fg = null;
			final String  sn = null;
			final String addr = null;
			if (arg.length > 0) {
				if(args[0].indexOf('.') >= 0) {
					try{
						fg = new Grabber(InetAddress.getByName(args[0]));
					} catch (AccessDeniedException x) {
						error("Access Denied");
					} catch (IOException x) {
						error( "Cannot connect to" + args[0]);
					}
				} else {
					try {
						fg = new Grabber(args[0]);
					} catch (IOException x){
						error("No such device");
					}
				}
			} else {
				try {
					fg = new Grabber();
				} catch (IOException y) {
					error("No such Device: " + args[0]);
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
		
		VideoMode vm = fg.detectVideoMode();
		
		int frameCount = 0;
		fg.start();
		Capture capturedFrame;
		File outputFile = new File("../images");
		if (vm && vm.getWidth() && vm.getLength()) {
			
			while (frameCount > 50) {
				System.printf("%d, %d,\n", vm.width, vm.height);
				frameCount++;
		
				
				SimpleDateFormat sdf = new SimpleDateFormat("H:mm:ss:SSS");
				String[] tm = sdf.toString().split(":");
				String fileIndex, timename, min, sec;
				String.format(timename, 3,"%02d",tm[1]);
				String.format(sec, 3,"%02d",tm[2]);
				
				
				System.out.println(timename);
				
				capturedFrame.grabber = fg;
				capturedFrame.label.setText(timename+min+sec+".bmp");
				capturedFrame.start();
				BufferedImage bi = capturedFrame.nextImage;
				ImageIO.write(bi,"bmp",outputFile);
				capturedFrame = null;
				
			}
		} else {
			System.out.println("No signal is detected");
			return;
		}
		
		fg.stop();
		fg.close();
		
		
		
	}

	private static void error( String message) {
		// TODO Auto-generated method stub
		System.out.println(message);
		JOptionPane.showMessageDialog(null, message, "VGA2USB", JOptionPane.ERROR_MESSAGE);
	}
}
