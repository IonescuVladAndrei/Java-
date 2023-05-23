package ImageEditorV3.packWork.packImageSizeFactory;

public class Segment_10 implements ImageSize {
    private int fullWidth;
    private int fullHeight;

    public Segment_10(int fullWidth, int fullHeight) {
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
        return this.fullHeight / 2 + 1;
    }

    @Override
    public int heightEnd() {
        return this.fullHeight - this.fullHeight / 4;
    }
}
