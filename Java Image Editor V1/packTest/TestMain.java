package packTest;
import java.io.*;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import packWork.*;

public class TestMain {
    public static void main(String[] args) {
        BufferedImage buffImage = null;
        int height, width;
        try {
            ImgPath img = new ImgPath();
            img.setpath();
            FileReader input = null;
            try {
                input = new FileReader(img.getInputPath());
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
            ExecutionWriterResult timeValues = new ExecutionWriterResult(0,0,0,0,0,0,0,0,0,0,0,0);
            int R, G, B;
            R = G = B = 0;
            System.out.println("Introduceti 3 valori intre 0 si 255 pentru a vedea daca se regaseste in imagine un pixel cu aceste valori: ");
            System.out.println("R = ");
            
            Scanner myInput = new Scanner( System.in );
            R  = myInput.nextInt();
            System.out.println("G = ");
            G  = myInput.nextInt();
            System.out.println("B = ");
            B  = myInput.nextInt();;
            myInput.close();
            PixelFound pixel = new PixelFound(R,G,B);
            buffImage = ImageIO.read(new File(img.getInputPath()));
            height = buffImage.getHeight();
            width = buffImage.getWidth();
            System.out.println("Main: height = " + height + " width = " + width);
            Buffer b = new Buffer();
            b.setSize(width, height);
            
            BufferWriteResult br = new BufferWriteResult();
            br.setSize(width, height);
            
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
           
            ImageProducer leftTopCornerRead = new ImageProducer(b, 0, width/2, 0, height/2, img.getInputPath(), timeValues); //sunt initializate 4 threduri pentru citirea fiecarui sfert de informatie            
            ImageProducer rightTopCornerRead = new ImageProducer(b, width/2 + 1, width-1,0, height/2, img.getInputPath(), timeValues);
            ImageProducer leftBottomCornerRead = new ImageProducer(b, 0, width/2, height/2+1, height-1, img.getInputPath(), timeValues);
            ImageProducer rightBottomCornerRead = new ImageProducer(b, width/2 + 1, width-1, height/2 + 1, height-1, img.getInputPath(), timeValues);
			
	    ImageConsumer leftTopCornerWrite = new ImageConsumer(b, 0, width/2, 0, height/2, outLeftTopCorner, timeValues, pixel);
            ImageConsumer rightTopCornerWrite = new ImageConsumer(b, width/2 + 1, width-1,0, height/2, outRightTopCorner, timeValues, pixel);
            ImageConsumer leftBottomCornerWrite = new ImageConsumer(b, 0, width/2,height/2+1, height-1, outLeftBottomCorner, timeValues, pixel);
            ImageConsumer rightBottomCornerWrite = new ImageConsumer(b, width/2 + 1, width-1, height/2 + 1, height-1, outRightBottomCorner, timeValues, pixel);
            
            WriterResult leftTopCornerWriterResult = new WriterResult(inLeftTopCorner, br, img.getInputPath(), img.getOutputPath(), timeValues);
            WriterResult rightTopCornerWriterResult= new WriterResult(inRightTopCorner, br, img.getInputPath(), img.getOutputPath(), timeValues);
            WriterResult leftBottomCornerWriterResult= new WriterResult(inLeftBottomCorner, br, img.getInputPath(), img.getOutputPath(), timeValues);
            WriterResult rightBottomCornerWriterResult= new WriterResult(inRightBottomCorner, br, img.getInputPath(), img.getOutputPath(), timeValues);
            
			
            leftTopCornerRead.start();
            rightTopCornerRead.start();
            leftBottomCornerRead.start();
            rightBottomCornerRead.start();
            
            leftTopCornerWrite.start();
            rightTopCornerWrite.start();
            leftBottomCornerWrite.start();
            rightBottomCornerWrite.start();
            
            leftTopCornerWriterResult.start();
            rightTopCornerWriterResult.start();
            leftBottomCornerWriterResult.start();
            rightBottomCornerWriterResult.start();      
        } catch (IOException e) {
            System.out.println("Eroare la citirea din fisier!");
            e.printStackTrace();
        } finally {
        }
        
         
    }
}
