package com.matlab.image;

/**
 * @author yinhao
 */
public class ImageUtil {

    /**
     * 根据传入参数，将其对应的rgb三色像素相加
     *
     * @param rgb  数组
     * @param rnum 红通道
     * @param gnum 绿通道
     * @param bnum 蓝通道
     * @return
     */
    public static int[][][] add(int[][][] rgb, int rnum, int gnum, int bnum) {  //色彩增强
        FileOpera op = FileOpera.getInstance();
        int width = op.getWidth();
        int height = op.getHeight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //红通道
                rgb[i][j][0] = rgb[i][j][0] + rnum;
                //绿通道
                rgb[i][j][1] = rgb[i][j][1] + gnum;
                //蓝通道
                rgb[i][j][2] = rgb[i][j][2] + bnum;
                //执行了截断算法
                if (rgb[i][j][0] > 255) {
                    rgb[i][j][0] = 255;
                } else if (rgb[i][j][0] < 0) {
                    rgb[i][j][0] = 0;
                }
                if (rgb[i][j][1] > 255) {
                    rgb[i][j][1] = 255;
                } else if (rgb[i][j][1] < 0) {
                    rgb[i][j][1] = 0;
                }
                if (rgb[i][j][2] > 255) {
                    rgb[i][j][2] = 255;
                } else if (rgb[i][j][2] < 0) {
                    rgb[i][j][2] = 0;
                }
            }
        }

        return rgb;
    }


    /**
     * 16进制的 #00ffee 转换为 rgb 数组相互转换
     *
     * @param math
     * @return
     */
    public static int[] MathToRgb(int math) {
        int[] rgb_n = new int[3];
        rgb_n[0] = (math & 0xff0000) >> 16;
        rgb_n[1] = (math & 0xff00) >> 8;
        rgb_n[2] = (math & 0xff);

        return rgb_n;
    }

    /**
     * rgb 数组 转换为   16进制的 #00ffee
     *
     * @param rgb
     * @return
     * @throws Exception
     */
    public static int RgbToMath(int[] rgb) throws Exception {
        String m1 = Integer.toHexString(rgb[0]);
        String m2 = Integer.toHexString(rgb[1]);
        String m3 = Integer.toHexString(rgb[2]);
        if (m1.length() < 2) {
            m1 = "0" + m1;
        }
        if (m2.length() < 2) {
            m2 = "0" + m2;
        }
        if (m3.length() < 2) {
            m3 = "0" + m3;
        }
        String max;
        max = m1 + m2 + m3;
        return Integer.valueOf(max, 16);
    }

    private static long lastClickTime = -1;

    /**
     * 防止过快点击多个时间
     *
     * @return
     */
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        return false;
    }
}
