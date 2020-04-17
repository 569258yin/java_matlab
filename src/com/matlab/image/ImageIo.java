package com.matlab.image;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * @author yinhao
 */
public class ImageIo extends JComponent{

	private static final long serialVersionUID = -2236160343614397287L; 

	public ImageIo() { 
		super(); 
		this.setOpaque(false); 
	} 

	public  void setRGB( BufferedImage image, int x, int y, int width, int height, int[] pixels ) { 
		int type = image.getType(); 
		if ( type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB ){
			image.getRaster().setDataElements( x, y, width, height, pixels ); 
		}
		else{ 
			image.setRGB( x, y, width, height, pixels, 0, width ); 
		}
	} 

	public static void main(String[] args) { 
		JFrame frame = new JFrame("Noise Art Panel"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.getContentPane().setLayout(new BorderLayout()); 

		// Display the window. 
		frame.getContentPane().add(new ImageIo(), BorderLayout.CENTER); 
		frame.setPreferredSize(new Dimension(400 + 25,450)); 
		frame.pack(); 
		frame.setVisible(true); 
	} 
} 


