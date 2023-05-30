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

public class ImageConsumerContrast extends Thread implements ImageConsumer{
	private ImageBuffer buffer;
	private int position;
	private ImagePath inputPath;
	private DataOutputStream out;
	private int filterValue;
	private int execTime;
	
	public ImageConsumerContrast(ImageBuffer b, int position, ImagePath inputPath, int filterValue,DataOutputStream out) {
		this.buffer = b;
		this.position = position;
		this.inputPath = inputPath;
		this.filterValue = filterValue;
		this.out = out;
	}
	
	private void setExecTime(int execTime) {
		this.execTime = execTime;
		System.out.println("Thread-ul " + this.position + " a durat " + this.execTime + " ms");
	}
	
	@Override
	public int getExecTime() {
		return this.execTime;
	}
	
	private int overLimit(int value) {
		if (value > 255)
			return 255;
		else if (value < 0)
			return 0;
		return value;
	}
	
	@Override
	public void run() {
		int start = (int)System.currentTimeMillis();
		int i, j, widthStart, widthEnd, heightStart, heightEnd, R, G, B;
		File file = new File(inputPath.getPath());
		Color color = null;
		BufferedImage image = null;

		try {
			image = ImageIO.read(file);

			ImageFactory imageFactory = new ImageFactory();
			ImageSize imageSize = imageFactory.createSize(this.position, image.getWidth(), image.getHeight());
			widthStart = imageSize.widthStart();
			widthEnd = imageSize.widthEnd();
			heightStart = imageSize.heightStart();
			heightEnd = imageSize.heightEnd();

			out.writeInt(widthStart);
			out.writeInt(widthEnd);
			out.writeInt(heightStart);
			out.writeInt(heightEnd);
			R = G = B = 0;

			for (i = widthStart; i <= widthEnd; i++) {
				for (j = heightStart; j <= heightEnd; j++) {
					color = buffer.get(i, j);
					if(this.filterValue < 0)
                		this.filterValue = 0;
                	double F = (259*(255+this.filterValue))/(255*(259-this.filterValue));
                	R = this.overLimit((int)(F*(color.getRed()-128)+128));
                	G = this.overLimit((int)(F*(color.getGreen()-128)+128));
                	B = this.overLimit((int)(F*(color.getBlue()-128)+128));
					out.writeInt(R);
					out.writeInt(G);
					out.writeInt(B);
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
			int end = (int)System.currentTimeMillis();
			this.setExecTime(end - start);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}