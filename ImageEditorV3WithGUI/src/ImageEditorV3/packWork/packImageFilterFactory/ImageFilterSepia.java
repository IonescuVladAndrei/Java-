package ImageEditorV3.packWork.packImageFilterFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Color;
import javafx.scene.image.ImageView;

import ImageEditorV3.packWork.ImageWriterClass;
import ImageEditorV3.packWork.ImagePaths;
import ImageEditorV3.packWork.packImageSizeFactory.ImageSize;
import ImageEditorV3.packWork.packImageSizeFactory.ImageFactory;

public class ImageFilterSepia extends Thread implements ImageFilter {
    private BufferedImage buffImage;
    private ImagePaths imagePaths;
    private int segmentVlaue;
    private int execTime;
    private static int nrOfThreadsDone = 0;
    private ImageView imageView;

    public ImageFilterSepia(BufferedImage buffImage, ImagePaths imagePaths, int segmentVlaue, ImageView imageView) {
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
        double tr, tg, tb;
        Color color = null;
        try {
            ImageFactory imageFactory = new ImageFactory();
            ImageSize imageSize = imageFactory.createSize(this.segmentVlaue, buffImage.getWidth(), buffImage.getHeight());
            for (i = imageSize.widthStart(); i <= imageSize.widthEnd(); i++)
                for (j = imageSize.heightStart(); j <= imageSize.heightEnd(); j++) {
                    color = new Color(buffImage.getRGB(i, j));
                    tr = 0.393 * color.getRed() + 0.769 * color.getGreen() + 0.189 * color.getBlue();
                    tg = 0.349 * color.getRed() + 0.686 * color.getGreen() + 0.168 * color.getBlue();
                    tb = 0.272 * color.getRed() + 0.534 * color.getGreen() + 0.131 * color.getBlue();
                    R = this.overLimit((int) tr);
                    G = this.overLimit((int) tg);
                    B = this.overLimit((int) tb);
                    buffImage.setRGB(i, j, 65536 * R + 256 * G + B);
                }

            if (this.segmentVlaue == 16) {
                while (ImageFilterSepia.nrOfThreadsDone < 15)
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        System.out.println("Error: Thread.sleep(10) (ImageFilterSepia)");
                        e.printStackTrace();
                    }
                end = (int) System.currentTimeMillis();
                ImageWriterClass imageWriterClass = new ImageWriterClass(imagePaths, buffImage, end - start, "ImageFilterSepia", "-", imageView);
                imageWriterClass.writeImage();
            }
            end = (int) System.currentTimeMillis();
            this.setExecTime(end - start);
            ImageFilterSepia.nrOfThreadsDone++;
        } catch (IOException e) {
            System.out.println("Error in thread: " + this.segmentVlaue);
            e.printStackTrace();
        }
    }
}
