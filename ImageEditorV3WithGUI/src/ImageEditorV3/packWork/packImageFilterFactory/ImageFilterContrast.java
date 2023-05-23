package ImageEditorV3.packWork.packImageFilterFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Color;
import javafx.scene.image.ImageView;

import ImageEditorV3.packWork.ImagePaths;
import ImageEditorV3.packWork.ImageWriterClass;
import ImageEditorV3.packWork.packImageSizeFactory.ImageSize;
import ImageEditorV3.packWork.packImageSizeFactory.ImageFactory;

public class ImageFilterContrast extends Thread implements ImageFilter {
    private BufferedImage buffImage;
    private ImagePaths imagePaths;
    private int segmentVlaue;
    private int inputFilterValue;
    private int execTime;
    private static int nrOfThreadsDone = 0;
    private ImageView imageView;

    public ImageFilterContrast(BufferedImage buffImage, ImagePaths imagePaths, int segmentVlaue, int inputFilterValue, ImageView imageView) {
        this.buffImage = buffImage;
        this.imagePaths = imagePaths;
        this.segmentVlaue = segmentVlaue;
        this.inputFilterValue = inputFilterValue;
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
        double F;
        Color color = null;
        try {
            ImageFactory imageFactory = new ImageFactory();
            ImageSize imageSize = imageFactory.createSize(this.segmentVlaue, buffImage.getWidth(), buffImage.getHeight());
            for (i = imageSize.widthStart(); i <= imageSize.widthEnd(); i++)
                for (j = imageSize.heightStart(); j <= imageSize.heightEnd(); j++) {
                    color = new Color(buffImage.getRGB(i, j));
                    if (this.inputFilterValue < 0)
                        this.inputFilterValue = 0;
                    F = (259 * (255 + this.inputFilterValue)) / (255 * (259 - this.inputFilterValue));
                    R = this.overLimit((int) (F * (color.getRed() - 128) + 128));
                    G = this.overLimit((int) (F * (color.getGreen() - 128) + 128));
                    B = this.overLimit((int) (F * (color.getBlue() - 128) + 128));
                    buffImage.setRGB(i, j, 65536 * R + 256 * G + B);
                }

            if (this.segmentVlaue == 16) {
                while (ImageFilterContrast.nrOfThreadsDone < 15)
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        System.out.println("Eroare: Thread.sleep(1000) (ImageFilterContrast)");
                        e.printStackTrace();
                    }
                end = (int) System.currentTimeMillis();
                ImageWriterClass imageWriterClass = new ImageWriterClass(imagePaths, buffImage, end - start, "ImageFilterContrast", String.valueOf(this.inputFilterValue), imageView);
                imageWriterClass.writeImage();
            }
            end = (int) System.currentTimeMillis();
            this.setExecTime(end - start);
            ImageFilterContrast.nrOfThreadsDone++;
        } catch (IOException e) {
            System.out.println("Error in thread: " + this.segmentVlaue);
            e.printStackTrace();
        }
    }
}
