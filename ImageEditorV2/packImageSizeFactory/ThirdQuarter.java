package packImageSizeFactory;

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
        return this.fullHeight - 1;
    }
}

