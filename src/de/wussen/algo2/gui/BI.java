package de.wussen.algo2.gui;

import java.awt.image.BufferedImage;
import java.util.Random;

public class BI extends BufferedImage{

	public BI(int width, int height, int imageType) {
		super(width, height, imageType);
	}

	@Override
	public void setRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize) {
		super.setRGB(startX, startY, w, h, rgbArray, offset, scansize);
	}
	
	public synchronized void setRGB1(int[] pix, int W, int H) {
		Random rnd = new Random();
		int size = W*H;
		
		for(int i = 0; i < size; ++i) {
			pix[i] = rnd.nextInt();
		}
		
		int line = 0;
		Object pixel = null;

		for (int y = 0; y < H; y++) {
			line = W*y;
            for (int x = 0; x < W; x++) {
                pixel = getColorModel().getDataElements(pix[line++], pixel);
                getRaster().setDataElements(x, y, pixel);
            }
        }

	}
	
	public synchronized void setRGB2(int W, int H) {
		Random rnd = new Random();
		
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {						
				Object pixel = null;
		        pixel = getColorModel().getDataElements(rnd.nextInt(), pixel);
		        getRaster().setDataElements(x, y, pixel);
			}
		}
	}
	
}
