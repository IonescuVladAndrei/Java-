package packWork;

import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Color;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageProducer extends ImgPath {
	protected Buffer buffer;
	protected int widthStart; // variabile declarate pentru fiecare sfert de informatie
	protected int widthEnd;
	protected int heightStart;
	protected int heightEnd;
	protected ExecutionWriterResult timeValues;

    public ImageProducer(Buffer b, int widthStart, int widthEnd, int heightStart, int heightEnd, String inputPath, ExecutionWriterResult timeValues) {
        buffer = b;
        this.widthStart = widthStart;
        this.widthEnd = widthEnd;
        this.heightStart = heightStart;
        this.heightEnd = heightEnd;
        this.inputPath = inputPath;
        this.timeValues = timeValues;
    }

    public void run() {
    	int start = (int)System.currentTimeMillis();
        File file = new File(inputPath);
        BufferedImage img = null;
        try {
            img = ImageIO.read(file); 
            int i, j, pixel;
            for (i = widthStart; i <= widthEnd; i++) {
                for (j = heightStart; j <= heightEnd; j++) {
                    pixel = img.getRGB(i, j);
                    Color color = new Color(pixel, true);
                    buffer.put(color, i, j);
                    if(i == widthEnd && j == heightEnd){
						if(widthStart == 0 && heightStart == 0){
							System.out.println("ImageProducer: Am transmis primul segment catre ImageConsumer.");
						}
						else if (heightStart == 0){
							System.out.println("ImageProducer: Am transmis al doilea segment catre ImageConsumer.");
						}
						else if (widthStart == 0){
							System.out.println("ImageProducer: Am transmis al treilea segment catre ImageConsumer.");
						}
						else {
							System.out.println("ImageProducer: Am transmis al patrulea segment catre ImageConsumer.");
						}
					}
                }
            }
        } catch (IOException e) {
            System.out.println("Eroare la citirea din fisier!");
            e.printStackTrace();

        }
        int end = (int)System.currentTimeMillis();
		if(widthStart == 0 && heightStart == 0){
			timeValues.setExecutionTimeProducer(1, end-start, 0, 0, 0);
		}
		else if (heightStart == 0){
			timeValues.setExecutionTimeProducer(2, 0, end-start, 0, 0);
		}
		else if (widthStart == 0){
			timeValues.setExecutionTimeProducer(3, 0, 0, end-start, 0);
		}
		else {
			timeValues.setExecutionTimeProducer(4,0, 0, 0, end-start);
		}
        try {
            sleep(1000);
        } catch (InterruptedException e) {
        	e.printStackTrace();
        }
    }
}
