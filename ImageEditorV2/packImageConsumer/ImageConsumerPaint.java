package packImageConsumer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import packImageSizeFactory.ImageFactory;
import packImageSizeFactory.ImageSize;
import packWork.*;

public class ImageConsumerPaint extends Thread implements ImageConsumer {
	private ImageBuffer buffer;
	private int position;
	private ImagePath inputPath;
	private DataOutputStream out;
	private int execTime;
	private ImageBuffer bTemp;

	public ImageConsumerPaint(ImageBuffer b, int position, ImagePath inputPath, int filterValue, DataOutputStream out, ImageBuffer bTemp) {
		this.buffer = b;
		this.position = position;
		this.inputPath = inputPath;
		this.out = out;
		this.bTemp = bTemp;
	}

	private void setExecTime(int execTime) {
		this.execTime = execTime;
		System.out.println("Thread-ul " + this.position + " a durat " + this.execTime + " ms");
	}

	@Override
	public int getExecTime() {
		return this.execTime;
	}

	@Override
	public void run() {
		int start = (int) System.currentTimeMillis();
		int i, j, widthStart, widthEnd, heightStart, heightEnd, R, G, B;
		File file = new File(inputPath.getPath());
		BufferedImage image = null;
		Color color;

		try {
			image = ImageIO.read(file);

			ImageFactory imageFactory = new ImageFactory();
			ImageSize imageSize = imageFactory.createSize(this.position, image.getWidth(), image.getHeight());
			widthStart = imageSize.widthStart();
			widthEnd = imageSize.widthEnd();
			heightStart = imageSize.heightStart();
			heightEnd = imageSize.heightEnd();
			R = G = B = 0;
			
			int distance = 10; // valoare aleasa de mine, poate ar trebui sa o schimb
			int k, l, maxR = 0, maxG = 0, maxB = 0;
			int[] arrayRed = new int[257];
			int[] arrayBlue = new int[257];
			int[] arrayGreen = new int[257];
			
			while (buffer.getNrOfThDoneP()!=4) {
	            try {
	            	Thread.sleep(50);                             
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
			
			
			for (i = widthStart; i <= widthEnd; i++) {
				for (j = heightStart; j <= heightEnd; j++) {
					
					
					for (k = 0; k < 256; k++)
					{
						arrayRed[k] = 0;
						arrayBlue[k] = 0;
						arrayGreen[k] = 0;
					}
					maxR = maxG = maxB = 0;
					
					
					for (k = i - distance; k <= i + distance; k++)
						for (l = j - distance; l <= j + distance; l++)
							if (k > -1 && l > -1 && k < image.getWidth() && l < image.getHeight()){
								color = buffer.get(k, l);
								arrayRed[color.getRed()]++;
								arrayBlue[color.getBlue()]++;
								arrayGreen[color.getGreen()]++;
							}
					
					
					for (k = 0; k < 256; k++) {
						if(maxR < arrayRed[k]) {
							maxR = arrayRed[k];
							R = k;
						}
						if(maxG < arrayGreen[k]) {
							maxG = arrayGreen[k];
							G = k;
						}
						if(maxB < arrayBlue[k]) {
							maxB = arrayBlue[k];
							B = k;
						}
						bTemp.put(new Color(R,G,B,255), i, j);
					}
				}
			}
			
			bTemp.threadDone();
			int cicles = 0;
			while (bTemp.getNrOfThDone() != 4) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					System.out.println("Eroare: Thread.sleep(50) (ImageConsumerPaint)");
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
				cicles++;
				if (cicles > 200) {
					System.out
							.println("Eroare: am trecut prin prea multe cicluri de asteptare!(ImageConsumerPaint)");
					Thread.currentThread().interrupt();
				}

			}
			System.out.println(
					"ImageConsumerPaint " + this.position + ": Am avut de asteptat: " + cicles * 50 + " ms");
			out.writeInt(widthStart);
			out.writeInt(widthEnd);
			out.writeInt(heightStart);
			out.writeInt(heightEnd);
			
			for (i = widthStart; i <= widthEnd; i++) {
				for (j = heightStart; j <= heightEnd; j++) {		
					color = bTemp.get(i, j);
					out.writeInt(color.getRed());
					out.writeInt(color.getGreen());
					out.writeInt(color.getBlue());
					
					if (i == widthEnd && j == heightEnd) {
						if (widthStart == 0 && heightStart == 0) {
							System.out.println("ImageConsumer: Am transmis primul segment catre WriterResult.");
						} else if (heightStart == 0) {
							System.out.println("ImageConsumer: Am transmis al doilea segment catre WriterResult.");
						} else if (widthStart == 0) {
							System.out.println("ImageConsumer: Am transmis al treilea segment catre WriterResult.");
						} else {
							System.out.println("ImageConsumer: Am transmis al patrulea segment catre WriterResult.");
						}
					}
				}
			}
			
			
			
			
			
			
			
			
			
			
			
			out.close();
			int end = (int) System.currentTimeMillis();
			this.setExecTime(end - start);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
