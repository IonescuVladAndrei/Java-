package ImageEditorV3.packWork.packImageFilterFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Color;
import javafx.scene.image.ImageView;

import ImageEditorV3.packWork.ImageWriterClass;
import ImageEditorV3.packWork.ImagePaths;
import ImageEditorV3.packWork.packImageSizeFactory.ImageSize;
import ImageEditorV3.packWork.packImageSizeFactory.ImageFactory;

public class ImageFilterPaintAlt extends Thread implements ImageFilter {
    private BufferedImage buffImage;
    private ImagePaths imagePaths;
    private int segmentVlaue;
    private int execTime;
    private static int nrOfThreadsDone = 0;
    private ImageView imageView;
    //private int inputFilterValue; private BufferedImage originalBuffImage;

    public ImageFilterPaintAlt(BufferedImage buffImage, ImagePaths imagePaths, int segmentVlaue, int inputFilterValue, ImageView imageView){
        this.buffImage = buffImage;
        this.imagePaths = imagePaths;
        this.segmentVlaue = segmentVlaue;
        this.imageView = imageView;
        //this.inputFilterValue = inputFilterValue;
    }

    private void setExecTime(int execTime) {
        this.execTime = execTime;
        System.out.println("Thread-ul " + this.segmentVlaue + " a durat " + this.execTime + " ms");
    }

    @Override
    public int getExecTime() {
        return this.execTime;
    }

    @Override
    public void run() {
        int start = (int) System.currentTimeMillis(), end, i, j, R, G, B;//, M, m;
        double Rd, Gd, Bd, H;//, S, V;
        Color color = null;
        try {
            ImageFactory imageFactory = new ImageFactory();
            ImageSize imageSize = imageFactory.createSize(this.segmentVlaue, buffImage.getWidth(), buffImage.getHeight());
            for (i = imageSize.widthStart(); i <= imageSize.widthEnd(); i++)
                for (j = imageSize.heightStart(); j <= imageSize.heightEnd(); j++) {
                    color = new Color(buffImage.getRGB(i, j));
                    R = color.getRed();
                    G = color.getGreen();
                    B = color.getBlue();
                    Rd = R;
                    Gd = G;
                    Bd = B;
                    // These values could be used in a different filter M = Math.max(R, Math.max(G, B)); m = Math.min(R, Math.min(G, B)); V = (double) M / 255; if (M == 0) S = 0; else S = (double) (1 - m / M);
                    if (G >= B)
                        H = 60 * Math.acos((Rd - 0.5 * Gd - 0.5 * Bd) / Math.sqrt(Rd * Rd + Gd * Gd + Bd * Bd - Rd * Gd - Rd * Bd - Gd * Bd));
                    else
                        H = 360 - 60 * Math.acos((Rd - 0.5 * Gd - 0.5 * Bd) / Math.sqrt(Rd * Rd + Gd * Gd + Bd * Bd - Rd * Gd - Rd * Bd - Gd * Bd));
                    if (H < 15) // red
                        buffImage.setRGB(i, j, 16711680);
                    else if (H < 45) // orange
                        buffImage.setRGB(i, j, 16753920);
                    else if (H < 65) // yellow
                        buffImage.setRGB(i, j, 16513796);
                    else if (H < 75) // green 1
                        buffImage.setRGB(i, j, 13564674);
                    else if (H < 90) // green 2
                        buffImage.setRGB(i, j, 8190976);
                    else if (H < 130) // green 3
                        buffImage.setRGB(i, j, 60673);
                    else if (H < 160) // green 4
                        buffImage.setRGB(i, j, 64154);
                    else if (H < 190) // green 5
                        buffImage.setRGB(i, j, 65535);
                    else if (H < 220) // blue 1
                        buffImage.setRGB(i, j, 2003199);
                    else if (H < 250) // blue 2
                        buffImage.setRGB(i, j, 255);
                    else if (H < 280) // purple
                        buffImage.setRGB(i, j, 9055202);
                    else if (H < 310) // pink
                        buffImage.setRGB(i, j, 16711935);
                    else if (H < 340) // pinkish red
                        buffImage.setRGB(i, j, 15416418);
                    else // red
                        buffImage.setRGB(i, j, 16711680);
                }

            if (this.segmentVlaue == 16) {
                while (ImageFilterPaintAlt.nrOfThreadsDone < 15)
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        System.out.println("Error: Thread.sleep(10) (ImageFilterPaintAlt)");
                        e.printStackTrace();
                    }
                    end = (int) System.currentTimeMillis();
                    ImageWriterClass imageWriterClass = new ImageWriterClass(imagePaths, buffImage, end - start, "ImageFilterPaintAlt", "-", imageView);
                    imageWriterClass.writeImage();
            }
            end = (int) System.currentTimeMillis();
            this.setExecTime(end - start);
            ImageFilterPaintAlt.nrOfThreadsDone++;
        } catch (

        IOException e) {
            System.out.println("Error in thread: " + this.segmentVlaue);
            e.printStackTrace();
        }
    }
}