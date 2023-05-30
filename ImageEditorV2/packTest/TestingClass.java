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
		boolean stopCond = false;
		ImagePath ImagePathInput = new ImagePath(), ImagePathOutput = new ImagePath();
		ImagePathInput.setPath(0);
		ImagePathOutput.setPath(1);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String inputCommand = "-1";
		while (inputCommand.compareTo("S") != 0) {
			if(stopCond){
				inputCommand = reader.readLine();
				System.out.println("Ati introdus: " + inputCommand);
			}
			stopCond = true;
			if (inputCommand.equals("S"))
				System.out.println("Programul se va opri acum");
			else {
				System.out.println("Doriti lista cu filtre? Y/N");
				inputCommand = reader.readLine();
				if(inputCommand.equals("Y"))
					System.out.println("Lista filtre:\nNegare -> 1\nGrey V1 -> 2\nGrey V2 -> 3\nLuminozitate -> 4\nSaturatie -> 5\nGamma -> 6\nSepia -> 7\nContrast -> 8\nOglindire -> 9\nFiltrare rosu -> 10\nFiltrare verde -> 11\nFiltrare albastru -> 12");
				inputCommand = "0";
				while (Integer.parseInt(inputCommand) < 1 || Integer.parseInt(inputCommand) > 12) {
					System.out.println("Introduceti ID-ul filtrului (1-12)");
					inputCommand = reader.readLine();
					System.out.println("Ati introdus ID-ul: " + inputCommand);
				}
				filter = Integer.parseInt(inputCommand);
				if(filter > 3 && filter < 13 ) {
					inputCommand = "256";
					while (Integer.parseInt(inputCommand) < -255 || Integer.parseInt(inputCommand) > 255) {
						System.out.println("Introduceti valoarea filtrului (intre -255 si +255)");
						inputCommand = reader.readLine();
						System.out.println("Ati introdus: " + inputCommand);
					}
				}
				filterValue = Integer.parseInt(inputCommand);
				try {
					FileReader input = null;
					try {
						input = new FileReader(ImagePathInput.getPath());
					} catch (FileNotFoundException e) {
						System.out.println("Fisierul nu a fost gasit!");
						System.out.println("Exceptie : " + e.getMessage());

					} finally {
						if (input != null) {
							System.out.println("Inchidem fisierul input.");
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
				
				tempImage.setSize(width, height);
				ImageConsumerFactory imageConsumerFactory = new ImageConsumerFactory();
				// thread-uri pentru imaginea temporara
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
						stockImg, ImagePathInput);
				ImageWriter rightTopCornerWriterResult = new ImageWriter(inRightTopCorner, imgOutput, ImagePathOutput,
						stockImg, ImagePathInput);
				ImageWriter leftBottomCornerWriterResult = new ImageWriter(inLeftBottomCorner, imgOutput,
						ImagePathOutput, stockImg, ImagePathInput);
				ImageWriter rightBottomCornerWriterResult = new ImageWriter(inRightBottomCorner, imgOutput,
						ImagePathOutput,stockImg, ImagePathInput);

				leftTopCornerWriterResult.start();
				rightTopCornerWriterResult.start();
				leftBottomCornerWriterResult.start();
				rightBottomCornerWriterResult.start();
				
			}
		}
	}
}
