package packWork;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class WriterResult extends ImgPath{
	protected DataInputStream in;
	protected BufferWriteResult br;
	 protected ExecutionWriterResult timeValues;
    
    public WriterResult (DataInputStream in, BufferWriteResult br, String inputPath, String outputPath, ExecutionWriterResult timeValues){
        this.in = in;
        this.br = br;
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.timeValues = timeValues;
    }

    public void run() {
    	int start = (int)System.currentTimeMillis();
    	int widthStart, widthEnd, heightStart, heightEnd;
    	int R, G, B;
    	int finalPixel;		//variabila declarata pentru scrierea imaginii
    	int i, j;
    	{
    		i = j = finalPixel = R = G = B = widthStart = widthEnd = heightStart = heightEnd = 0;
    	}
		try {
			finalPixel = in.readInt();
			widthStart = in.readInt();
			widthEnd = in.readInt();
			heightStart = in.readInt();
			heightEnd = in.readInt();
			for (i = widthStart; i <= widthEnd; i++) {
				for (j = heightStart; j <= heightEnd; j++) {
					
						R = in.readInt();
						G = in.readInt();
						B = in.readInt();
						Color color = new Color(R, G, B);
						br.put(color, i, j);
						if(i == widthEnd && j == heightEnd){
							if(widthStart == 0 && heightStart == 0){
								System.out.println("WrierResult: Am receptat primul segment de la ImageConsumer.");
							}
							else if (heightStart == 0){
								System.out.println("WrierResult: Am receptat al doilea segment de la ImageConsumer.");
							}
							else if (widthStart == 0){
								System.out.println("WrierResult: Am receptat al treilea segment de la ImageConsumer.");
							}
							else {
								System.out.println("WrierResult: Am receptat al patrulea segment de la ImageConsumer.");
							}
						}
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
      	if(finalPixel == 1){
      		try{
      			Thread.sleep(2000);			//plasat pentru a rezolva un memory leak foarte enervant
      		} catch (InterruptedException e) {
      			Thread.currentThread().interrupt();
      		}
      		File file = new File(inputPath);
          	BufferedImage img = null;
           	try{
           		img = ImageIO.read(file);
           	} catch (IOException e) {
            	System.out.println("Eroare la citirea din fisier!(WrierResult)");
           		e.printStackTrace();
           	}
           	for (i = 0; i <= widthEnd ; i ++){
           		for (j = 0; j <= heightEnd; j++) {
           			 Color color = br.get(i, j);
           			 img.setRGB(i, j, color.getRGB());			
           		}
           	}
           	file = new File(outputPath);
           	try{
           		ImageIO.write(img, "bmp", file);
           	} catch (IOException e) {
           		System.out.println("Eroare la scrierea fisierului!(WrierResult)");
            	e.printStackTrace();
            }                    
        }
      	int end = (int)System.currentTimeMillis();
		if(widthStart == 0 && heightStart == 0){
			timeValues.setExecutionWriterResult(1, end-start, 0, 0, 0);
		}
		else if (heightStart == 0){
			timeValues.setExecutionWriterResult(2, 0, end-start, 0, 0);
		}
		else if (widthStart == 0){
			timeValues.setExecutionWriterResult(3, 0, 0, end-start, 0);
		}
		else {
			timeValues.setExecutionWriterResult(4,0, 0, 0, end-start);
		}
		if(finalPixel == 1){
			int k;
			for(k=1;k<5;k++)
				System.out.println("Thread-ul ImageProducer " + k + " a durat " + timeValues.getExecutionTimeProducer(k) + " ms.");
			for(k=1;k<5;k++)
				System.out.println("Thread-ul ImageConsumer " + k + " a durat " + timeValues.getExecutionTimeConsumer(k) + " ms.");
			for(k=1;k<5;k++){
				if(k!=4)
					System.out.println("Thread-ul WriterResult " + k + " a durat " + timeValues.getExecutionWriterResult(k) + " ms.");
				else
					System.out.println("Thread-ul WriterResult " + k + " a durat " + timeValues.getExecutionWriterResult(k) + " ms. (-2000 ms adaugate artificial)");
			}
			timeValues.longestProducerThread(timeValues.getExecutionTimeProducer(1), timeValues.getExecutionTimeProducer(2), timeValues.getExecutionTimeProducer(3), timeValues.getExecutionTimeProducer(4));
			timeValues.longestConsumerThread(timeValues.getExecutionTimeConsumer(1),timeValues.getExecutionTimeConsumer(2),timeValues.getExecutionTimeConsumer(3),timeValues.getExecutionTimeConsumer(4));
			timeValues.longestWriterThread(timeValues.getExecutionWriterResult(1),timeValues.getExecutionWriterResult(2),timeValues.getExecutionWriterResult(3),timeValues.getExecutionWriterResult(4));
		}
		
	}   
}

