package ImageEditorV3.packWork;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.nio.file.Paths;
import javafx.scene.image.ImageView;

import ImageEditorV3.packWork.packImageFilterFactory.*;

public class EditorMain {

    public EditorMain() {
    }

    public void runApp(String inputImageName, String outputImageName, int inputFilter, int inputFilterValue, boolean keepMetadata, String watermarkName, int watermarkPlace, int keepColor, ImageView imageView) throws IOException {
        System.out.println("EditorMain: received for input: " + inputImageName + "   | received for output: " + outputImageName);
        ImagePaths imagePaths = new ImagePaths(inputImageName,  outputImageName, watermarkName, watermarkPlace, keepColor, keepMetadata);
        System.out.println("Absolute path for input image: " + Paths.get(imagePaths.getPathInitialImage()).toAbsolutePath().toFile());
        BufferedImage buffImage = ImageIO.read(Paths.get(imagePaths.getPathInitialImage()).toAbsolutePath().toFile());

        ImageFilterFactory imageConsumerFactory = new ImageFilterFactory();
        ImageFilter ImageFilterSegment_1 = imageConsumerFactory.createImageFilter(buffImage, imagePaths, 1, inputFilter, inputFilterValue, imageView);
        ImageFilter ImageFilterSegment_2 = imageConsumerFactory.createImageFilter(buffImage, imagePaths, 2, inputFilter, inputFilterValue, imageView);
        ImageFilter ImageFilterSegment_3 = imageConsumerFactory.createImageFilter(buffImage, imagePaths, 3, inputFilter, inputFilterValue, imageView);
        ImageFilter ImageFilterSegment_4 = imageConsumerFactory.createImageFilter(buffImage, imagePaths, 4, inputFilter, inputFilterValue, imageView);
        ImageFilter ImageFilterSegment_5 = imageConsumerFactory.createImageFilter(buffImage, imagePaths, 5, inputFilter, inputFilterValue, imageView);
        ImageFilter ImageFilterSegment_6 = imageConsumerFactory.createImageFilter(buffImage, imagePaths, 6, inputFilter, inputFilterValue, imageView);
        ImageFilter ImageFilterSegment_7 = imageConsumerFactory.createImageFilter(buffImage, imagePaths, 7, inputFilter, inputFilterValue, imageView);
        ImageFilter ImageFilterSegment_8 = imageConsumerFactory.createImageFilter(buffImage, imagePaths, 8, inputFilter, inputFilterValue, imageView);
        ImageFilter ImageFilterSegment_9 = imageConsumerFactory.createImageFilter(buffImage, imagePaths, 9, inputFilter, inputFilterValue, imageView);
        ImageFilter ImageFilterSegment_10 = imageConsumerFactory.createImageFilter(buffImage, imagePaths, 10, inputFilter, inputFilterValue, imageView);
        ImageFilter ImageFilterSegment_11 = imageConsumerFactory.createImageFilter(buffImage, imagePaths, 11, inputFilter, inputFilterValue, imageView);
        ImageFilter ImageFilterSegment_12 = imageConsumerFactory.createImageFilter(buffImage, imagePaths, 12, inputFilter, inputFilterValue, imageView);
        ImageFilter ImageFilterSegment_13 = imageConsumerFactory.createImageFilter(buffImage, imagePaths, 13, inputFilter, inputFilterValue, imageView);
        ImageFilter ImageFilterSegment_14 = imageConsumerFactory.createImageFilter(buffImage, imagePaths, 14, inputFilter, inputFilterValue, imageView);
        ImageFilter ImageFilterSegment_15 = imageConsumerFactory.createImageFilter(buffImage, imagePaths, 15, inputFilter, inputFilterValue, imageView);
        ImageFilter ImageFilterSegment_16 = imageConsumerFactory.createImageFilter(buffImage, imagePaths, 16, inputFilter, inputFilterValue, imageView);

        ImageFilterSegment_1.start();
        ImageFilterSegment_2.start();
        ImageFilterSegment_3.start();
        ImageFilterSegment_4.start();
        ImageFilterSegment_5.start();
        ImageFilterSegment_6.start();
        ImageFilterSegment_7.start();
        ImageFilterSegment_8.start();
        ImageFilterSegment_9.start();
        ImageFilterSegment_10.start();
        ImageFilterSegment_11.start();
        ImageFilterSegment_12.start();
        ImageFilterSegment_13.start();
        ImageFilterSegment_14.start();
        ImageFilterSegment_15.start();
        ImageFilterSegment_16.start();
    }
}
