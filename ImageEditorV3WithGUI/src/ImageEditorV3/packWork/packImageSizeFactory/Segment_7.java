package ImageEditorV3.packWork.packImageSizeFactory;

public class Segment_7 implements ImageSize {
    private int fullWidth;
    private int fullHeight;

    public Segment_7(int fullWidth, int fullHeight) {
        this.fullWidth = fullWidth;
        this.fullHeight = fullHeight;
    }

    @Override
    public int widthStart() {
        return this.fullWidth / 2 + 1;
    }

    @Override
    public int widthEnd() {
        return this.fullWidth - this.fullWidth / 4;
    }

    @Override
    public int heightStart() {
        return this.fullHeight / 4 + 1;
    }

    @Override
    public int heightEnd() {
        return this.fullHeight / 2;
    }
}
