package packTest;

import packWork.*;

public class ImageSizeTesting {
    public static void main(String[] args)
    {
        ImageFactory imageFactory = new ImageFactory();
        //cadran 4, width 4000, height 3000
        ImageSize imageSize = imageFactory.createSize(4, 4000, 3000);    
        System.out.println(imageSize.widthStart());
        System.out.println(imageSize.widthEnd());
        System.out.println(imageSize.heightStart());
        System.out.println(imageSize.heightEnd());
    }
}
