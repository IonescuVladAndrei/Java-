package packImageSizeFactory;

public class FirstQuarter implements ImageSize {
    private int fullWidth;
    private int fullHeight;

    public FirstQuarter(int fullWidth, int fullHeight) {
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
        return 0;
    }
    
    @Override
    public int heightEnd() {
        return this.fullHeight / 2;
    }
}

