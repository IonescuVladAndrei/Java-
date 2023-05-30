package packWork;

import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Color;
import java.io.IOException;
import javax.imageio.ImageIO;

import packImageSizeFactory.*;

public class ImageProducer extends Thread {
	private ImageBuffer buffer;
	private int position;
	private ImagePath inputPath;

	public ImageProducer(ImageBuffer b, int position, ImagePath inputPath) {
		this.buffer = b;
		this.position = position;
		this.inputPath = inputPath;
	}

	public void run() {
		File file = new File(inputPath.getPath());
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
			int i, j, pixel, widthStart, widthEnd, heightStart, heightEnd;
			//in functie de cadran 1,2,3,4 populam ImageBuffer-ul
			ImageFactory imageFactory = new ImageFactory();
	        ImageSize imageSize = imageFactory.createSize(this.position, image.getWidth(), image.getHeight());
	        widthStart = imageSize.widthStart();
	        widthEnd = imageSize.widthEnd();
	        heightStart = imageSize.heightStart();
	        heightEnd = imageSize.heightEnd();
			
			
			for (i = widthStart; i <= widthEnd; i++) {
				for (j = heightStart; j <= heightEnd; j++) {
					pixel = image.getRGB(i, j);
					
					Color color = new Color(pixel, true);
					buffer.put(color, i, j);
					if (i == widthEnd && j == heightEnd) {
						if (widthStart == 0 && heightStart == 0) {
							buffer.threadDoneP();
							System.out.println("ImageProducer: Am transmis primul segment catre ImageConsumer.");
						} else if (heightStart == 0) {
							buffer.threadDoneP();
							System.out.println("ImageProducer: Am transmis al doilea segment catre ImageConsumer.");
						} else if (widthStart == 0) {
							buffer.threadDoneP();
							System.out
									.println("ImageProducer: Am transmis al treilea segment catre ImageConsumer.");
						} else {
							buffer.threadDoneP();
							System.out
									.println("ImageProducer: Am transmis al patrulea segment catre ImageConsumer.");
						}
					}
				}
				
			}
		} catch (IOException e) {
			System.out.println("Eroare la citirea din fisier!");
			e.printStackTrace();
		}
	}
}
