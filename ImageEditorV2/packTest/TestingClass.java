package packTest;

import packWork.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import javax.imageio.ImageIO;

import packImageConsumer.*;

public class TestingClass {

	public static void main(String[] args) throws IOException {
		BufferedImage buffImage = null;
		int height, width, filter, filterValue = 0;
		// ImagePath ImagePathInput = new ImagePath(), ImagePathOutput = new
		// ImagePath();
		// ImagePathInput.setPath(0);
		// ImagePathOutput.setPath(1);

		//ImagePath ImagePathInput = new ImagePath("C:\\Users\\Vlad\\Desktop\\Java dev\\Images\\input\\house.png"),
		//		ImagePathOutput = new ImagePath("C:\\Users\\Vlad\\Desktop\\Java dev\\Images\\temp\\temp.png");
		ImagePath ImagePathInput = new ImagePath("Images/input/house.png"),
				ImagePathOutput = new ImagePath("Images/temp/temp.png");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String inputCommand = "temp";
		while (inputCommand.compareTo("s") != 0) {
			System.out.println("Intr comanda: ");
			inputCommand = reader.readLine();
			System.out.println("Ati introdus: " + inputCommand);
			if (inputCommand.equals("s"))
				System.out.println("Programul se va opri acum");
			
			try {
				FileReader input = null;
				try {
					input = new FileReader(ImagePathInput.getPath());
				} catch (FileNotFoundException e) {
					System.out.println("Fisierul nu a fost gasit!");
					System.out.println("Exceptie : " + e.getMessage());

				} finally {
					if (input != null) {
						// Inchidem fisierul
						System.out.println("Inchidem fisierul");
						try {
							input.close();
						} catch (IOException e) {
							System.out.println("Nu poate fi inchis!");
							e.printStackTrace();
						}
					}
				}

			} finally {
			}

			buffImage = ImageIO.read(new File(ImagePathInput.getPath()));
			height = buffImage.getHeight();
			width = buffImage.getWidth();
			System.out.println("Main: height = " + height + " width = " + width);

			ImageBuffer stockImg = new ImageBuffer(), tempImage = new ImageBuffer();
			stockImg.setSize(width, height);

			// pipe-uri pentru rgb
			PipedOutputStream pipeOutLeftTopCorner = new PipedOutputStream();
			PipedInputStream pipeInLeftTopCorner = new PipedInputStream(pipeOutLeftTopCorner);
			DataOutputStream outLeftTopCorner = new DataOutputStream(pipeOutLeftTopCorner);
			DataInputStream inLeftTopCorner = new DataInputStream(pipeInLeftTopCorner);

			PipedOutputStream pipeOutRightTopCorner = new PipedOutputStream();
			PipedInputStream pipeInRightTopCorner = new PipedInputStream(pipeOutRightTopCorner);
			DataOutputStream outRightTopCorner = new DataOutputStream(pipeOutRightTopCorner);
			DataInputStream inRightTopCorner = new DataInputStream(pipeInRightTopCorner);

			PipedOutputStream pipeOutLeftBottomCorner = new PipedOutputStream();
			PipedInputStream pipeInLeftBottomCorner = new PipedInputStream(pipeOutLeftBottomCorner);
			DataOutputStream outLeftBottomCorner = new DataOutputStream(pipeOutLeftBottomCorner);
			DataInputStream inLeftBottomCorner = new DataInputStream(pipeInLeftBottomCorner);

			PipedOutputStream pipeOutRightBottomCorner = new PipedOutputStream();
			PipedInputStream pipeInRightBottomCorner = new PipedInputStream(pipeOutRightBottomCorner);
			DataOutputStream outRightBottomCorner = new DataOutputStream(pipeOutRightBottomCorner);
			DataInputStream inRightBottomCorner = new DataInputStream(pipeInRightBottomCorner);

			// thread-uri pentru imaginea input
			ImageProducer leftTopCornerRead = new ImageProducer(stockImg, 1, ImagePathInput);
			ImageProducer rightTopCornerRead = new ImageProducer(stockImg, 2, ImagePathInput);
			ImageProducer leftBottomCornerRead = new ImageProducer(stockImg, 3, ImagePathInput);
			ImageProducer rightBottomCornerRead = new ImageProducer(stockImg, 4, ImagePathInput);

			leftTopCornerRead.start();
			rightTopCornerRead.start();
			leftBottomCornerRead.start();
			rightBottomCornerRead.start();
			// thread-uri pentru imaginea temporara

			filter = 1; // 1 2 3 5
			filterValue = 100;

			if (filterValue > 255)
				filterValue = 255;
			tempImage.setSize(width, height);

			ImageConsumerFactory imageConsumerFactory = new ImageConsumerFactory();

			ImageConsumer leftTopCornerWrite = imageConsumerFactory.createImageConsumer(filter, stockImg, 1,
					ImagePathInput, filterValue, outLeftTopCorner, tempImage);
			ImageConsumer rightTopCornerWrite = imageConsumerFactory.createImageConsumer(filter, stockImg, 2,
					ImagePathInput, filterValue, outRightTopCorner, tempImage);
			ImageConsumer leftBottomCornerWrite = imageConsumerFactory.createImageConsumer(filter, stockImg, 3,
					ImagePathInput, filterValue, outLeftBottomCorner, tempImage);
			ImageConsumer rightBottomCornerWrite = imageConsumerFactory.createImageConsumer(filter, stockImg, 4,
					ImagePathInput, filterValue, outRightBottomCorner, tempImage);
			leftTopCornerWrite.start();
			rightTopCornerWrite.start();
			leftBottomCornerWrite.start();
			rightBottomCornerWrite.start();

			ColorModel cm = buffImage.getColorModel();
			boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
			WritableRaster raster = buffImage.copyData(null);
			BufferedImage imgOutput = new BufferedImage(cm, raster, isAlphaPremultiplied, null);

			ImageWriter leftTopCornerWriterResult = new ImageWriter(inLeftTopCorner, imgOutput, ImagePathOutput,
					stockImg);
			ImageWriter rightTopCornerWriterResult = new ImageWriter(inRightTopCorner, imgOutput, ImagePathOutput,
					stockImg);
			ImageWriter leftBottomCornerWriterResult = new ImageWriter(inLeftBottomCorner, imgOutput, ImagePathOutput,
					stockImg);
			ImageWriter rightBottomCornerWriterResult = new ImageWriter(inRightBottomCorner, imgOutput, ImagePathOutput,
					stockImg);

			leftTopCornerWriterResult.start();
			rightTopCornerWriterResult.start();
			leftBottomCornerWriterResult.start();
			rightBottomCornerWriterResult.start();

		}
		// https://cloudinary.com/guides/bulk-image-resize/3-ways-to-resize-images-in-java
		// posibil resize

		// secventa de citire comenzi
		/*
		 * BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		 * String inputCommand = "temp"; while(inputCommand.compareTo("s")!=0) {
		 * System.out.println("Intr comanda: "); inputCommand = reader.readLine();
		 * System.out.println("Ati introdus: " + inputCommand);
		 * if(inputCommand.equals("s")) System.out.println("Programul se va opri acum");
		 * }
		 */
	}

}
