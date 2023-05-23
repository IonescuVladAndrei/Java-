package ImageEditorV3.packWork.packImageFilterFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javafx.scene.image.ImageView;

import ImageEditorV3.packWork.ImagePaths;

public class ImageFilterFactory {
    public ImageFilter createImageFilter(BufferedImage buffImage, ImagePaths imagePaths, int segmentValue, int inputFilter, int inputFilterValue, ImageView imageView) throws IOException {
        switch (inputFilter) {
            case 1:
                return new ImageFilterNegative(buffImage, imagePaths, segmentValue, imageView);
            case 2:
                return new ImageFilterGreyScaleV1(buffImage, imagePaths, segmentValue, imageView);
            case 3:
                return new ImageFilterGreyScaleV2(buffImage, imagePaths, segmentValue, imageView);
            case 4:
                return new ImageFilterBright(buffImage, imagePaths, segmentValue, inputFilterValue, imageView);
            case 5:
                return new ImageFilterSat(buffImage, imagePaths, segmentValue, inputFilterValue, imageView);
            case 6:
                return new ImageFilterGamma(buffImage, imagePaths, segmentValue, inputFilterValue, imageView);
            case 7:
                return new ImageFilterSepia(buffImage, imagePaths, segmentValue, imageView);
            case 8:
                return new ImageFilterContrast(buffImage, imagePaths, segmentValue, inputFilterValue, imageView);
            case 9:
                return new ImageFilterInvertByY(buffImage, imagePaths, segmentValue, imageView);
            case 10:
                return new ImageFilterPaint(buffImage, imagePaths, segmentValue, inputFilterValue, imageView);
            case 11:
                return new ImageFilterRed(buffImage, imagePaths, segmentValue, inputFilterValue, imageView);
            case 12:
                return new ImageFilterGreen(buffImage, imagePaths, segmentValue, inputFilterValue, imageView);
            case 13:
                return new ImageFileterBlue(buffImage, imagePaths, segmentValue, inputFilterValue, imageView);
            case 14:
                return new ImageFilterMixt(buffImage, imagePaths, segmentValue, imageView);
            case 15:
                return new ImageFilterMixt2(buffImage, imagePaths, segmentValue, imageView);
            case 16:
                return new ImageFilterInvertByX(buffImage, imagePaths, segmentValue, imageView);
            case 17:
                return new ImageFilterInvertByXY(buffImage, imagePaths, segmentValue, imageView);
            case 18:
                return new ImageFilterPaintAlt(buffImage, imagePaths, segmentValue, inputFilterValue, imageView);
            case 19:
                return new ImageFilterOutline(buffImage, imagePaths, segmentValue, imageView);
            case 20:
                return new ImageFilterWatermark(buffImage, imagePaths, segmentValue, imageView);
            default:
                throw new IllegalArgumentException("ImageFilterFactory: filtrul cu valoarea " + inputFilter + " nu a fost adaugat inca.");
        }
    }
}
