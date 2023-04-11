package packWork;

public class ThirdQuarter implements ImageSize {
    private int fullWidth;
    private int fullHeight;

    public ThirdQuarter(int fullWidth, int fullHeight) {
        this.fullWidth = fullWidth;
        this.fullHeight = fullHeight;
    }

    @Override
    public int widthStart() {
        return 0;
    }

    public int widthEnd() {
        return this.fullWidth / 2;
    }

    public int heightStart() {
        return this.fullHeight / 2 + 1;
    }

    public int heightEnd() {
        return this.fullHeight - 1;
    }
}
