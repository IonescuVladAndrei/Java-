package ImageEditorV3.packWork.packImageFilterFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.nio.file.Paths;
import javafx.scene.image.ImageView;

import ImageEditorV3.packWork.ImagePaths;
import ImageEditorV3.packWork.ImageWriterClass;
import ImageEditorV3.packWork.packImageSizeFactory.ImageSize;
import ImageEditorV3.packWork.packImageSizeFactory.ImageFactory;

public class ImageFilterOutline extends Thread implements ImageFilter {
    private BufferedImage buffImage;
    private ImagePaths imagePaths;
    private int segmentVlaue;
    private int execTime;
    private static int nrOfThreadsDone = 0;
    private BufferedImage originalBuffImage;
    private ImageView imageView;

    public ImageFilterOutline(BufferedImage buffImage, ImagePaths imagePaths, int segmentVlaue, ImageView imageView) throws IOException {
        this.originalBuffImage = ImageIO.read(Paths.get(imagePaths.getPathInitialImage()).toAbsolutePath().toFile());
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
        int start = (int) System.currentTimeMillis(), end, i, j;
        boolean chColor = false;
        try {
            ImageFactory imageFactory = new ImageFactory();
            ImageSize imageSize = imageFactory.createSize(this.segmentVlaue, buffImage.getWidth(), buffImage.getHeight());
            for (i = imageSize.widthStart(); i <= imageSize.widthEnd(); i++)
                for (j = imageSize.heightStart(); j <= imageSize.heightEnd(); j++) {
                    chColor = false;
                    if (i - 1 > 0)
                        if (this.originalBuffImage.getRGB(i, j) != this.originalBuffImage.getRGB(i - 1, j))
                            chColor = true;
                    if (i + 1 < this.originalBuffImage.getWidth())
                        if (this.originalBuffImage.getRGB(i, j) != this.originalBuffImage.getRGB(i + 1, j))
                            chColor = true;
                    if (j - 1 > 0)
                        if (this.originalBuffImage.getRGB(i, j) != this.originalBuffImage.getRGB(i, j - 1))
                            chColor = true;
                    if (j + 1 < this.originalBuffImage.getHeight())
                        if (this.originalBuffImage.getRGB(i, j) != this.originalBuffImage.getRGB(i, j + 1))
                            chColor = true;

                    if (chColor)
                        buffImage.setRGB(i, j, 0);
                    else
                        buffImage.setRGB(i, j, 16777215);
                }

            if (this.segmentVlaue == 16) {
                while (ImageFilterOutline.nrOfThreadsDone < 15)
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        System.out.println("Error: Thread.sleep(10) (ImageFilterOutline)");
                        e.printStackTrace();
                    }
                end = (int) System.currentTimeMillis();
                ImageWriterClass imageWriterClass = new ImageWriterClass(imagePaths, buffImage, end - start, "ImageFilterOutline", "-", imageView);
                imageWriterClass.writeImage();
            }
            end = (int) System.currentTimeMillis();
            this.setExecTime(end - start);
            ImageFilterOutline.nrOfThreadsDone++;
        } catch (IOException e) {
            System.out.println("Error in thread: " + this.segmentVlaue);
            e.printStackTrace();
        }
    }
}
