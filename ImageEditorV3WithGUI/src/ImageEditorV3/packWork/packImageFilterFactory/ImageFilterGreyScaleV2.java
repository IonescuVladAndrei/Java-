package ImageEditorV3.packWork.packImageFilterFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Color;
import javafx.scene.image.ImageView;

import ImageEditorV3.packWork.ImagePaths;
import ImageEditorV3.packWork.ImageWriterClass;
import ImageEditorV3.packWork.packImageSizeFactory.ImageSize;
import ImageEditorV3.packWork.packImageSizeFactory.ImageFactory;

public class ImageFilterGreyScaleV2 extends Thread implements ImageFilter {
    private BufferedImage buffImage;
    private ImagePaths imagePaths;
    private int segmentVlaue;
    private int execTime;
    private static int nrOfThreadsDone = 0;
    private ImageView imageView;

    public ImageFilterGreyScaleV2(BufferedImage buffImage, ImagePaths imagePaths, int segmentVlaue, ImageView imageView) {
        this.buffImage = buffImage;
        this.imagePaths = imagePaths;
        this.segmentVlaue = segmentVlaue;
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
        int start = (int) System.currentTimeMillis(), end, i, j, gs;
        Color color = null;
        try {
            ImageFactory imageFactory = new ImageFactory();
            ImageSize imageSize = imageFactory.createSize(this.segmentVlaue, buffImage.getWidth(), buffImage.getHeight());
            for (i = imageSize.widthStart(); i <= imageSize.widthEnd(); i++)
                for (j = imageSize.heightStart(); j <= imageSize.heightEnd(); j++) {
                    color = new Color(buffImage.getRGB(i, j));
                    gs = (int) (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue());
                    buffImage.setRGB(i, j, 65536 * gs + 256 * gs + gs);
                }

            if (this.segmentVlaue == 16) {
                while (ImageFilterGreyScaleV2.nrOfThreadsDone < 15)
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        System.out.println("Error: Thread.sleep(10) (ImageFilterGreyScaleV2)");
                        e.printStackTrace();
                    }
                end = (int) System.currentTimeMillis();
                ImageWriterClass imageWriterClass = new ImageWriterClass(imagePaths, buffImage, end - start, "ImageFilterGreyScaleV2", "-", imageView);
                imageWriterClass.writeImage();
            }
            end = (int) System.currentTimeMillis();
            this.setExecTime(end - start);
            ImageFilterGreyScaleV2.nrOfThreadsDone++;
        } catch (IOException e) {
            System.out.println("Error in thread: " + this.segmentVlaue);
            e.printStackTrace();
        }
    }
}
