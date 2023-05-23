package ImageEditorV3.packWork.packImageSizeFactory;

public class ImageFactory {
    public ImageSize createSize(int position, int fullWidth, int fullHeight) {
        if (fullWidth == 0 || fullHeight == 0) {
            System.out.println("ImageFactory: fullWidth or fullHeight is 0!");
            return null;
        }
        switch (position) {
            case 1:
                return new Segment_1(fullWidth, fullHeight);
            case 2:
                return new Segment_2(fullWidth, fullHeight);
            case 3:
                return new Segment_3(fullWidth, fullHeight);
            case 4:
                return new Segment_4(fullWidth, fullHeight);
            case 5:
                return new Segment_5(fullWidth, fullHeight);
            case 6:
                return new Segment_6(fullWidth, fullHeight);
            case 7:
                return new Segment_7(fullWidth, fullHeight);
            case 8:
                return new Segment_8(fullWidth, fullHeight);
            case 9:
                return new Segment_9(fullWidth, fullHeight);
            case 10:
                return new Segment_10(fullWidth, fullHeight);
            case 11:
                return new Segment_11(fullWidth, fullHeight);
            case 12:
                return new Segment_12(fullWidth, fullHeight);
            case 13:
                return new Segment_13(fullWidth, fullHeight);
            case 14:
                return new Segment_14(fullWidth, fullHeight);
            case 15:
                return new Segment_15(fullWidth, fullHeight);
            case 16:
                return new Segment_16(fullWidth, fullHeight);
            default:
                throw new IllegalArgumentException("ImageFactory: invalid segment: " + position);
        }
    }
}
