package com.matlab.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author yinhao
 * 灰度处理方法
 */
public class Rgb2Gray {

    private FileOpera op = FileOpera.getInstance();
    private FilePath filepath = FilePath.getInstance();
    int width = op.getWidth();
    int height = op.getHeight();

    public void run() {
        int[][] ave = op.rgbRead2gray();
        //灰度变化的操作
        //重点，技巧在这个参数BufferedImage.TYPE_BYTE_GRAY
        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grayImage.setRGB(i, j, ave[i][j]);
            }
        }
        String str = filepath.getUripath_new();
        try {
            ImageIO.write(grayImage, "png", new File(str));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
