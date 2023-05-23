package ImageEditorV3.packWork.packImageFilterFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.nio.file.Paths;
import java.awt.Color;
import javafx.scene.image.ImageView;


import ImageEditorV3.packWork.ImagePaths;
import ImageEditorV3.packWork.packImageSizeFactory.ImageSize;
import ImageEditorV3.packWork.packImageSizeFactory.ImageFactory;
import ImageEditorV3.packWork.ImageWriterClass;

public class ImageFilterPaint extends Thread implements ImageFilter {
    private BufferedImage buffImage;
    private ImagePaths imagePaths;
    private int segmentVlaue;
    private int inputFilterValue;
    private int execTime;
    private static int nrOfThreadsDone = 0;
    private BufferedImage originalBuffImage;
    private ImageView imageView;

    public ImageFilterPaint(BufferedImage buffImage, ImagePaths imagePaths, int segmentVlaue, int inputFilterValue, ImageView imageView) throws IOException {
        this.originalBuffImage = ImageIO.read(Paths.get(imagePaths.getPathInitialImage()).toAbsolutePath().toFile());
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

    @Override
    public int getExecTime() {
        return this.execTime;
    }

    @Override
    public void run() {
        int start = (int) System.currentTimeMillis(), end, i, j, R, G, B;
        int k, l, maxR = 0, maxG = 0, maxB = 0;
        int[] arrayRed = new int[257];
        int[] arrayBlue = new int[257];
        int[] arrayGreen = new int[257];
        R = G = B = 0;
        // higher values => increased completion time
        int distance;
        if (this.inputFilterValue < 20)
            distance = 2;
        else if (this.inputFilterValue > 259)
            distance = 25;
        else
            distance = this.inputFilterValue / 10;

        Color color = null;
        try {
            ImageFactory imageFactory = new ImageFactory();
            ImageSize imageSize = imageFactory.createSize(this.segmentVlaue, buffImage.getWidth(), buffImage.getHeight());
            for (i = imageSize.widthStart(); i <= imageSize.widthEnd(); i++)
                for (j = imageSize.heightStart(); j <= imageSize.heightEnd(); j++) {
                    for (k = 0; k < 256; k++) {
                        arrayRed[k] = 0;
                        arrayBlue[k] = 0;
                        arrayGreen[k] = 0;
                    }
                    maxR = maxG = maxB = 0;

                    for (k = i - distance; k <= i + distance; k++)
                        for (l = j - distance; l <= j + distance; l++)
                            if (k > -1 && l > -1 && k < originalBuffImage.getWidth() && l < originalBuffImage.getHeight()) {
                                color = new Color(originalBuffImage.getRGB(k, l));
                                arrayRed[color.getRed()]++;
                                arrayBlue[color.getBlue()]++;
                                arrayGreen[color.getGreen()]++;
                            }
                    for (k = 0; k < 256; k++) {
                        if (maxR < arrayRed[k]) {
                            maxR = arrayRed[k];
                            R = k;
                        }
                        if (maxG < arrayGreen[k]) {
                            maxG = arrayGreen[k];
                            G = k;
                        }
                        if (maxB < arrayBlue[k]) {
                            maxB = arrayBlue[k];
                            B = k;
                        }
                        buffImage.setRGB(i, j, 65536 * R + 256 * G + B);
                    }
                }
            if (this.segmentVlaue == 16) {
                while (ImageFilterPaint.nrOfThreadsDone < 15)
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        System.out.println("Error: Thread.sleep(10) (ImageFilterPaint)");
                        e.printStackTrace();
                    }
                end = (int) System.currentTimeMillis();
                ImageWriterClass imageWriterClass = new ImageWriterClass(imagePaths, buffImage, end - start, "ImageFilterPaint", String.valueOf(this.inputFilterValue)+ " -> "+ String.valueOf(distance), imageView);
                imageWriterClass.writeImage();
            }
            end = (int) System.currentTimeMillis();
            this.setExecTime(end - start);
            ImageFilterPaint.nrOfThreadsDone++;
        } catch (IOException e) {
            System.out.println("Error in thread: " + this.segmentVlaue);
            e.printStackTrace();
        }
    }
}