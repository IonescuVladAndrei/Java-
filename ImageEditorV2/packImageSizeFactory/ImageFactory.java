package packImageSizeFactory;

public class ImageFactory {
    public ImageSize createSize(int position, int fullWidth, int fullHeight) {
        if (fullWidth == 0 || fullHeight == 0) {
            System.out.println("ImageFactory: fullWidth sau fullHeight este 0!");
            return null;
        }
        switch (position) {
            case 1:
                return new FirstQuarter(fullWidth, fullHeight);
            case 2:
                return new SecondQuarter(fullWidth, fullHeight);
            case 3:
                return new ThirdQuarter(fullWidth, fullHeight);
            case 4:
                return new ForthQuarter(fullWidth, fullHeight);
            default:
                throw new IllegalArgumentException("ImageFactory: in afara cadranului "+ position);
        }
    }
}
