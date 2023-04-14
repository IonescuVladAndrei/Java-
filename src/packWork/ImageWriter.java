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

    public ImageWriter(DataInputStream id, BufferedImage imgOutput, ImagePath outputPath, ImageBuffer buffer) {
        this.inputData = id;
        this.imgOutput = imgOutput;
        this.outputPath = outputPath;
        this.buffer = buffer;
    }

    public void run() {
        // int start = (int)System.currentTimeMillis();
        int widthStart, widthEnd, heightStart, heightEnd, R, G, B, i, j; 
        Color color;
        try {
        	
            widthStart = inputData.readInt();
            
            widthEnd = inputData.readInt();
            heightStart = inputData.readInt();
            heightEnd = inputData.readInt();
            //System.out.println("widthStart " + widthStart + " widthEnd " + widthEnd + " heightStart " + heightStart + " heightEnd " + heightEnd);
                for (i = widthStart; i <= widthEnd; i++) {
                    for (j = heightStart; j <= heightEnd; j++) {

                        R = inputData.readInt();
                        G = inputData.readInt();
                        B = inputData.readInt();
                        //System.out.println("Am primit R = " + R);
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
                    if (cicles == 200000) {
                        System.out.println("Eroare: am trecut prin prea multe cicluri de asteptare!(ImageWriter)");
                        Thread.currentThread().interrupt();
                    }

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
                    System.out.println("ImageWriter: Imaginea a fost scrisa cu succes!");
                } catch (IOException e) {
                    System.out.println("Eroare la scrierea fisierului!(ImageWriter)");
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * int end = (int)System.currentTimeMillis();
         * if(widthStart == 0 && heightStart == 0){
         * timeValues.setExecutionWriterResult(1, end-start, 0, 0, 0);
         * }
         * else if (heightStart == 0){
         * timeValues.setExecutionWriterResult(2, 0, end-start, 0, 0);
         * }
         * else if (widthStart == 0){
         * timeValues.setExecutionWriterResult(3, 0, 0, end-start, 0);
         * }
         * else {
         * timeValues.setExecutionWriterResult(4,0, 0, 0, end-start);
         * }
         * if(finalPixel == 1){
         * int k;
         * for(k=1;k<5;k++)
         * System.out.println("Thread-ul ImageProducer " + k + " a durat " +
         * timeValues.getExecutionTimeProducer(k) + " ms.");
         * for(k=1;k<5;k++)
         * System.out.println("Thread-ul ImageConsumer " + k + " a durat " +
         * timeValues.getExecutionTimeConsumer(k) + " ms.");
         * for(k=1;k<5;k++){
         * if(k!=4)
         * System.out.println("Thread-ul WriterResult " + k + " a durat " +
         * timeValues.getExecutionWriterResult(k) + " ms.");
         * else
         * System.out.println("Thread-ul WriterResult " + k + " a durat " +
         * timeValues.getExecutionWriterResult(k) +
         * " ms. (-2000 ms adaugate artificial)");
         * }
         * 
         * timeValues.longestProducerThread(timeValues.getExecutionTimeProducer(1),
         * timeValues.getExecutionTimeProducer(2),
         * timeValues.getExecutionTimeProducer(3),
         * timeValues.getExecutionTimeProducer(4));
         * timeValues.longestConsumerThread(timeValues.getExecutionTimeConsumer(1),
         * timeValues.getExecutionTimeConsumer(2),timeValues.getExecutionTimeConsumer(3)
         * ,timeValues.getExecutionTimeConsumer(4));
         * timeValues.longestWriterThread(timeValues.getExecutionWriterResult(1),
         * timeValues.getExecutionWriterResult(2),timeValues.getExecutionWriterResult(3)
         * ,timeValues.getExecutionWriterResult(4));
         */
    }

}
