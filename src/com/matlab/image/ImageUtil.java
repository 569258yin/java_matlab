package com.matlab.image;

public class ImageUtil {
	
	/**
	 * ���ݴ�������������Ӧ��rgb��ɫ�������
	 * @param rgb  ����
	 * @param rnum   ��ͨ��
	 * @param gnum   ��ͨ��
	 * @param bnum   ��ͨ��
	 * @return
	 */
	public static int[][][] add(int[][][] rgb,int rnum,int gnum,int bnum) {  //ɫ����ǿ
		FileOpera op = FileOpera.getInstance();
		int width = op.getWidth();
		int height = op.getHeight();
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				rgb[i][j][0] = rgb[i][j][0]+rnum;  //��ͨ��
				rgb[i][j][1] = rgb[i][j][1]+gnum;	//��ͨ��
				rgb[i][j][2] = rgb[i][j][2]+bnum;	//��ͨ��
				if (rgb[i][j][0]>255) {				//ִ���˽ض��㷨
					rgb[i][j][0] = 255;
				}else if(rgb[i][j][0] < 0){
					rgb[i][j][0] = 0;
				}
				if (rgb[i][j][1]>255) {
					rgb[i][j][1] = 255;
				}else if(rgb[i][j][1] < 0){
					rgb[i][j][1] = 0;
				}
				if (rgb[i][j][2]>255) {
					rgb[i][j][2] = 255;
				}else if(rgb[i][j][2] < 0){
					rgb[i][j][2] = 0;
				}
			}
		}

		return rgb;
	}
	
	
	/**
	 *  16���Ƶ� #00ffee ת��Ϊ rgb �����໥ת��
	 * @param math
	 * @return
	 */
	public static int[] MathToRgb(int math){
		int[] rgb_n = new int[3];
		rgb_n[0] = (math & 0xff0000) >> 16;
		rgb_n[1] = (math & 0xff00) >> 8;
		rgb_n[2] = (math & 0xff);

		return  rgb_n;
	}
	
	/**
	 * rgb ���� ת��Ϊ   16���Ƶ� #00ffee 
	 * @param rgb
	 * @return
	 * @throws Exception
	 */
	public static int RgbToMath(int[] rgb) throws Exception{
		String m1 = Integer.toHexString(rgb[0]);
		String m2 = Integer.toHexString(rgb[1]);
		String m3 = Integer.toHexString(rgb[2]);
		if (m1.length()<2) {
			m1 = "0"+m1;
		}
		if (m2.length()<2) {
			m2 = "0"+m2;
		}
		if (m3.length()<2) {
			m3 = "0"+m3;
		}
		String max;	
		max=m1+m2+m3; 
		//		System.out.println(max);
		int newNumber = Integer.valueOf(max,16);
		//		System.out.println(newNumber);
		//		m = m.toUpperCase();
		//		System.out.println(m);

		return newNumber;  

	}
	
	private static long lastClickTime = -1;
	/** 
	 * ��ֹ���������ʱ��
	 * @return
	 */
	public synchronized static boolean isFastClick() {
		long time = System.currentTimeMillis();
		if ( time - lastClickTime < 500) {   
			return true;   
		}   
		lastClickTime = time;   
		return false;   
	}
}
