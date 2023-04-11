package packWork;

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

    public int widthEnd() {
        return this.fullWidth / 2;
    }

    public int heightStart() {
        return 0;
    }

    public int heightEnd() {
        return this.fullHeight / 2;
    }
}
