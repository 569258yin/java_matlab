package com.matlab.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图片像素操作类
 *
 * @author YH
 */
public class FileOpera {

    private static FileOpera Instance = null;

    public static FileOpera getInstance() {
        if (Instance == null) {
            Instance = new FileOpera();
        }
        return Instance;
    }

    private FilePath filepath = FilePath.getInstance();
    private int width;
    private int height;
    private int[] rgb = new int[3];
    private int[][][] rgb_num;
    private BufferedImage bi;

    private FileOpera() {
        String uri = filepath.getUripath();
        File file = new File(uri);
        try {
            bi = ImageIO.read(file);
            width = bi.getWidth();
            height = bi.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 返回整个图像像素点的灰度值
     *
     * @return
     */
    public int[][] rgbRead2gray() {
        int[][] ave = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                ave[i][j] = bi.getRGB(i, j);
            }
        }
        return ave;
    }

    /**
     * 读取出图像的rgb三通道像素值
     *
     * @return
     */
    public int[][][] RgbRead() {
        int ave;
        rgb_num = new int[width][height][3];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                ave = bi.getRGB(i, j);
                rgb = ImageUtil.MathToRgb(ave);
                rgb_num[i][j][0] = rgb[0];
                rgb_num[i][j][1] = rgb[1];
                rgb_num[i][j][2] = rgb[2];
            }
        }
        return rgb_num;
    }

    /**
     * 重新设置图像的rgb像素
     *
     * @param rgb
     * @throws Exception
     */
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
                math = ImageUtil.RgbToMath(r);
                bi.setRGB(i, j, math);
                //我有一个十六进制字符串  "FF5D7E"，怎么把它转变成
                //0XFF5D7E 的int型
                //Integer.parseInt 不行
                //原因计算错误
                //bi.setRGB(i, j, 0XFF5D7E);
            }
        }
        //写入新的图片
        ImageIO.write(bi, "png", file);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
