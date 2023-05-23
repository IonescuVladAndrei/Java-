package ImageEditorV3.packWork.packImageFilterFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Color;
import javafx.scene.image.ImageView;

import ImageEditorV3.packWork.ImagePaths;
import ImageEditorV3.packWork.ImageWriterClass;
import ImageEditorV3.packWork.packImageSizeFactory.ImageSize;
import ImageEditorV3.packWork.packImageSizeFactory.ImageFactory;

public class ImageFilterMixt extends Thread implements ImageFilter {
    private BufferedImage buffImage;
    private ImagePaths imagePaths;
    private int segmentVlaue;
    private int execTime;
    private static int nrOfThreadsDone = 0;
    private ImageView imageView;

    public ImageFilterMixt(BufferedImage buffImage, ImagePaths imagePaths, int segmentVlaue, ImageView imageView) {
        this.buffImage = buffImage;
        this.imagePaths = imagePaths;
        this.segmentVlaue = segmentVlaue;
        this.imageView = imageView;
    }

    private void setExecTime(int execTime) {
        this.execTime = execTime;
        System.out.println("Thread nr " + this.segmentVlaue + " has taken " + this.execTime + " ms");
    }

    private int overLimit(int value) {
        if (value > 255)
            return 255;
        else if (value < 0)
            return 0;
        return value;
    }

    @Override
    public int getExecTime() {
        return this.execTime;
    }

    @Override
    public void run() {
        int start = (int) System.currentTimeMillis(), end, i, j, R, G, B;
        double tr, tg, tb, Y;
        Color color = null;
        Y = 2.3;
        try {
            ImageFactory imageFactory = new ImageFactory();
            ImageSize imageSize = imageFactory.createSize(this.segmentVlaue, buffImage.getWidth(), buffImage.getHeight());
            for (i = imageSize.widthStart(); i <= imageSize.widthEnd(); i++)
                for (j = imageSize.heightStart(); j <= imageSize.heightEnd(); j++) {
                    color = new Color(buffImage.getRGB(i, j));
                    tr = 0.393 * (255 - color.getRed()) + 0.769 * (255 - color.getGreen()) + 0.189 * (255 - color.getBlue());
                    tg = 0.349 * (255 - color.getRed()) + 0.686 * (255 - color.getGreen()) + 0.168 * (255 - color.getBlue());
                    tb = 0.272 * (255 - color.getRed()) + 0.534 * (255 - color.getGreen()) + 0.131 * (255 - color.getBlue());
                    R = this.overLimit((int) tr);
                    G = this.overLimit((int) tg);
                    B = this.overLimit((int) tb);
                    R = (int) (255 * Math.pow((double) R / 255, Y));
                    G = (int) (255 * Math.pow((double) G / 255, Y));
                    B = (int) (255 * Math.pow((double) B / 255, Y));
                    buffImage.setRGB(i, j, 65536 * R + 256 * G + B);
                }

            if (this.segmentVlaue == 16) {
                while (ImageFilterMixt.nrOfThreadsDone < 15)
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        System.out.println("Error: Thread.sleep(10) (ImageFilterMixt)");
                        e.printStackTrace();
                    }
                end = (int) System.currentTimeMillis();
                ImageWriterClass imageWriterClass = new ImageWriterClass(imagePaths, buffImage, end - start, "ImageFilterMixt", "-", imageView);
                imageWriterClass.writeImage();
            }
            end = (int) System.currentTimeMillis();
            this.setExecTime(end - start);
            ImageFilterMixt.nrOfThreadsDone++;
        } catch (IOException e) {
            System.out.println("Error in thread: " + this.segmentVlaue);
            e.printStackTrace();
        }
    }
}
