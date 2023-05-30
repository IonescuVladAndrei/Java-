package packWork;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageWriter extends Thread {

	private DataInputStream inputData;
    private BufferedImage imgOutput;
    private ImagePath outputPath;
    private ImageBuffer buffer;
    private ImagePath inputPath;

    public ImageWriter(DataInputStream id, BufferedImage imgOutput, ImagePath outputPath, ImageBuffer buffer, ImagePath inputPath) {
        this.inputData = id;
        this.imgOutput = imgOutput;
        this.outputPath = outputPath;
        this.buffer = buffer;
        this.inputPath = inputPath;
    }

    public void run() {
        int widthStart, widthEnd, heightStart, heightEnd, R, G, B, i, j; 
        Color color;
        try {
            widthStart = inputData.readInt();
            widthEnd = inputData.readInt();
            heightStart = inputData.readInt();
            heightEnd = inputData.readInt();
                for (i = widthStart; i <= widthEnd; i++) {
                    for (j = heightStart; j <= heightEnd; j++) {
                        R = inputData.readInt();
                        G = inputData.readInt();
                        B = inputData.readInt();
                        color = new Color(R, G, B, 255);
                        buffer.put(color, i, j);
                        if (i == widthEnd && j == heightEnd) {
                            if (widthStart == 0 && heightStart == 0) {
                                System.out.println("WrierResult: Am receptat primul segment de la ImageConsumer.");
                            } else if (heightStart == 0) {
                                System.out.println("WrierResult: Am receptat al doilea segment de la ImageConsumer.");
                            } else if (widthStart == 0) {
                                System.out.println("WrierResult: Am receptat al treilea segment de la ImageConsumer.");
                            } else {
                                System.out.println("WrierResult: Am receptat al patrulea segment de la ImageConsumer.");
                            }
                        }
                    }
                }
            inputData.close();
            if (widthStart == 0 || heightStart == 0) { // nu am ajuns in thread-ul 4, dar am terminat de scris pe disc
                buffer.threadDone();
            } else {
                int cicles = 0;
                while (buffer.getNrOfThDone() != 3) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        System.out.println("Eroare: Thread.sleep(50) (ImageWriter)");
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                    cicles++;
                }
                System.out.println("ImageWriter: Am avut de asteptat: " + cicles*50 + " ms");
                for (i = 0; i <= widthEnd; i++) {
                    for (j = 0; j <= heightEnd; j++) {
                    	color = buffer.get(i, j);
                    	imgOutput.setRGB(i, j, color.getRGB());
                    }
                }
                File outFile = new File(outputPath.getPath());
                try {
                    ImageIO.write(imgOutput, "png", outFile);
                    System.out.println("ImageWriter: Imaginea a fost scrisa cu succes!\nIntroduceti S pentru stop sau orice al caracter pentru continuare");
                    inputPath.setPath(outputPath.getPath());
                } catch (IOException e) {
                    System.out.println("Eroare la scrierea fisierului!(ImageWriter)");
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
