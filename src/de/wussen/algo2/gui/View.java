package de.wussen.algo2.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import de.wussen.algo2.main.Log;

public class View extends JFrame {
	
	final int W = 1280;
//	final int H = 720;
	final int H = 720*2;
	Image img;
	int[] pix = new int[W*H];
	int[] sprite = new int[16*16];
	MemoryImageSource mis;
	PixelGrabber pg;
	BufferedImage bi = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
	
	public View(String s) {
		super(s);
		setSize(W,H);
		
		mis = new MemoryImageSource(W, H, pix, 0, W);
		mis.setAnimated(true);
		img = createImage(mis);
		
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	public void asd() {
		rnd();
		rndNew();
	}
	
	private int times = 10;
	private int frames = 1000;
	
	public void rndNew() {
		BI bi2 = new BI(W, H, BufferedImage.TYPE_INT_ARGB);
		long[] a = new long[100];
		for (int o = 0; o < times; o++) {
			long startTime = System.currentTimeMillis();
			for(int t = 0; t < frames; ++t) {
				
//				bi2.setRGB1(pix, W, H);
				bi2.setRGB2(W, H);
				
				getGraphics().drawImage(bi2, 0, 0, this);
			}
			Log.info("New: " + String.valueOf((long) (System.currentTimeMillis() - startTime)));
			a[o] = System.currentTimeMillis() - startTime;
		} 
		long l = 0;
		for (int i = 0; i < a.length; i++) {
			l += a[i];
		}
		l /= times;
		Log.info("New Sum: " + l);
		
	}

	public void rnd() {
		Random rnd = new Random();
		int size = W*H;
		long[] a = new long[100];
		for (int o = 0; o < times; o++) {
			long startTime = System.currentTimeMillis();
			for(int t = 0; t < frames; ++t) {
				for(int i = 0; i < size; ++i) {
					pix[i] = rnd.nextInt();
				}
				mis.newPixels();
				getGraphics().drawImage(img, 0, 0, this);
			}
			Log.info("Old: " + String.valueOf((long) (System.currentTimeMillis() - startTime)));
			a[o] = System.currentTimeMillis() - startTime;
		} 
		long l = 0;
		for (int i = 0; i < a.length; i++) {
			l += a[i];
		}
		l /= times;
		Log.info("Old Sum: " + l);
	}
	
	public void langsam() {
		try {
			Image bi = ImageIO.read(new File("pattern_16px.png"));
			
			sprite = new int[16*16];
			
			pg = new PixelGrabber(bi, 0, 0, 16, 16, sprite, 0, 16);
			pg.grabPixels();
			
			mis = new MemoryImageSource(16, 16, sprite, 0, 16);
			mis.setAnimated(true);
			
			img = createImage(mis);
			mis.newPixels();
			for(int x = 0; x < 80; ++x) {
				for(int y = 0; y < 45; ++y) {					
					getGraphics().drawImage(img, 0 + (x * 16), 0 + (y * 16), this);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void schnell() {
		try {
			Image bi = ImageIO.read(new File("pattern_16px.png"));
			
			pg = new PixelGrabber(bi, 0, 0, 16, 16, sprite, 0, 16);
			pg.grabPixels();
			
			for (int i = 0; i < pix.length; i++) {
				pix[i] = sprite[i%16 + ((16*(i/W))%256) ];
			}

			mis = new MemoryImageSource(W, H, pix, 0, W);
			mis.setAnimated(true);
			
			img = createImage(mis);
			mis.newPixels();
			getGraphics().drawImage(img, 0, 50, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(Graphics g) {}
	public void paint(Graphics g) {
//		g.drawImage(img, x, y, observer)
	}

}
