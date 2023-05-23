package ImageEditorV3.packWork;

public class ImagePaths {
    private String pathInitialImage;
    private String pathFinalImage;
    private String watermarkPath;
    private int watermarkPlace;     // 1, 2, 3 pentru logo colt sus stanga, colt jos dreapta, mijloc 
    private int keepColor;  // 1 = keep color, 2 = blend, 3 = blend 2 , 4 = stand out
    private boolean keepMetadata;

    public ImagePaths(String pathInitialImage, String pathFinalImage, String watermarkPath, int watermarkPlace, int keepColor, boolean keepMetadata){
        this.pathInitialImage = pathInitialImage;
        this.pathFinalImage = pathFinalImage;
        this.watermarkPath = watermarkPath;
        this.watermarkPlace = watermarkPlace;
        this.keepColor = keepColor;
        this.keepMetadata = keepMetadata;
    }

    public String getPathInitialImage() {
		return this.pathInitialImage;
	}

    public String getPathFinalImage() {
		return this.pathFinalImage;
	}

    public String getWatermarkPath(){
        return this.watermarkPath;
    }

    public int getWatermarkPlace(){
        return this.watermarkPlace;
    }

    public int getKeepColor(){
        return this.keepColor;
    }

    public boolean getKeepMetadata(){
        return this.keepMetadata;
    }
}
