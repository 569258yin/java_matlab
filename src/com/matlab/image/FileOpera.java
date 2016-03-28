package com.matlab.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.BufferUnderflowException;

import javax.imageio.ImageIO;

public class FileOpera {
	private FilePath filepath = new FilePath();
	private int width;
	private int height;
	private int[] rgb = new int[3];
	private int[][][] rgb_num; 
	private DealRGB deal = new DealRGB();
	private BufferedImage bi;

	public FileOpera() {
		String uri = filepath.getUripath();
		File file = new File(uri);
		try {
			bi = ImageIO.read(file);
			width =  bi.getWidth();
			height = bi.getHeight();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int[][] rgbRead2gray() {
		int[][] ave = new int[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				ave[i][j] = bi.getRGB(i, j);
			}
		}
		return ave;
	}
	public int[][][] RgbRead() {
		int ave;
		rgb_num = new int[width][height][3];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				ave = bi.getRGB(i, j);
				rgb = deal.MathToRgb(ave);
				rgb_num[i][j][0] = rgb[0];
				rgb_num[i][j][1] = rgb[1];
				rgb_num[i][j][2] = rgb[2];
			}
		}
		return rgb_num;
	}
	
	public void RgbSet(int[][][] rgb) throws Exception {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int math = 0;
		int[] r = new int[3];
		String str = filepath.getUripath_new();
		File file = new File(str);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				r[0] = rgb[i][j][0];
				r[1] = rgb[i][j][1];
				r[2] = rgb[i][j][2];
				math = deal.RgbToMath(r);
				bi.setRGB(i, j, math);
				//����һ��ʮ�������ַ���  "FF5D7E"����ô����ת���
				//				0XFF5D7E ��int��
				//Integer.parseInt ���� 
				//ԭ��������
				//bi.setRGB(i, j, 0XFF5D7E);
			}
		}
		ImageIO.write(bi, "png",file);//д���µ�ͼƬ
		System.out.println("success");
	}
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
