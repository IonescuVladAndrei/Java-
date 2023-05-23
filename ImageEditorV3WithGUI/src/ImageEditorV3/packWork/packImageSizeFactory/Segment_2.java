package ImageEditorV3.packWork.packImageSizeFactory;

public class Segment_2 implements ImageSize {
    private int fullWidth;
    private int fullHeight;

    public Segment_2(int fullWidth, int fullHeight) {
        this.fullWidth = fullWidth;
        this.fullHeight = fullHeight;
    }

    @Override
    public int widthStart() {
        return this.fullWidth / 4 + 1;
    }

    @Override
    public int widthEnd() {
        return this.fullWidth / 2;
    }

    @Override
    public int heightStart() {
        return 0;
    }

    @Override
    public int heightEnd() {
        return this.fullHeight / 4;
    }
}
