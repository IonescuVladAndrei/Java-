package packWork;

public class ForthQuarter implements ImageSize {
    private int fullWidth;
    private int fullHeight;

    public ForthQuarter(int fullWidth, int fullHeight) {
        this.fullWidth = fullWidth;
        this.fullHeight = fullHeight;
    }
    @Override
    public int widthStart() {
        return this.fullWidth / 2 + 1;
    }
    
    @Override
    public int widthEnd() {
        return this.fullWidth - 1;
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
