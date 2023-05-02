package packWork;

import java.util.Scanner;

public class ImgPath extends Thread{

	protected String inputPath;
	protected String outputPath;
    
	static {
		System.out.println("ImgPath: static block init.");
	}
	
    public ImgPath() {
        this.inputPath = null;
        this.outputPath = null;
    }

    public ImgPath(String inputPath, String outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    public String getInputPath() {
        return this.inputPath;
    }

    public String getOutputPath() {
        return this.outputPath;
    }
    
    public void setpath() {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Introduceti numele imaginii input (fara extensie)");
        String imgName = myObj.nextLine();
        inputPath = "C:\\Images\\";
        inputPath = (inputPath.concat(imgName)).concat(".bmp");     //concatenare destinatie fisier de intrare
        System.out.println("Introduceti numele imaginii output (fara extensie)");
        imgName = myObj.nextLine();
        outputPath = "C:\\Images\\";
        outputPath = (outputPath.concat(imgName)).concat(".bmp");   //concatenare destinatie fisier de iesire
        System.out.println("Input path: " + inputPath);
        System.out.println("Output path: " + outputPath);
    }
}




