package com.matlab.image;

import java.util.concurrent.Callable;

/**
 * @author yinhao
 * 处理图片的线程任务
 */
public class DealTask implements Callable<Boolean> {
    private static int[][][] rgb;
    private int rNum, gNum, bNum;

    public DealTask(int r, int g, int b) {
        this.rNum = r;
        this.gNum = g;
        this.bNum = b;
    }

    @Override
    public Boolean call() {
        FileOpera op = FileOpera.getInstance();
        rgb = op.RgbRead();
        rgb = ImageUtil.add(rgb, rNum, gNum, bNum);
        try {
            op.RgbSet(rgb);
            return true;
        } catch (Exception e) {
            System.err.println("输出图像失败");
            e.printStackTrace();
        }
        return false;
    }

}
