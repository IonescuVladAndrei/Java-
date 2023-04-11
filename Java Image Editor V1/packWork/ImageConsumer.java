package packWork;
import java.awt.Color;
import java.io.DataOutputStream;
import java.io.IOException;


public class ImageConsumer extends Thread {
	protected Buffer buffer;
    protected int widthStart; 
    protected int widthEnd;
    protected int heightStart;
    protected int heightEnd;
    protected DataOutputStream out;
    protected ExecutionWriterResult timeValues;
    protected PixelFound pixel;


    public ImageConsumer (Buffer b, int widthStart, int widthEnd, int heightStart, int heightEnd, DataOutputStream out,  ExecutionWriterResult timeValues, PixelFound pixel) {
        buffer = b;
        this.widthStart = widthStart;
        this.widthEnd = widthEnd;
        this.heightStart = heightStart;
        this.heightEnd = heightEnd;
        this.out = out;
        this.timeValues = timeValues;
        this.pixel = pixel;
    }

    public void run() {
    	int start = (int)System.currentTimeMillis();
        int i, j, red, green, blue;
        Color color;
        try {
        	if(widthStart != 0 && heightStart != 0)//conditie pentru a transmite 1 daca am ajuns la ultimul segment
        		out.writeInt(1);
        	else
        		out.writeInt(0);
        	
        	out.writeInt(widthStart);
        	out.writeInt(widthEnd);
        	out.writeInt(heightStart);
        	out.writeInt(heightEnd);
        	for (i = widthStart; i <= widthEnd; i++) {
                for (j = heightStart; j <= heightEnd; j++){
                    color = buffer.get(i, j);
                    
                    if(pixel.getPixelValue(1) == 0 ){
                    	if(color.getRed() == pixel.getPixelValue(4) && color.getGreen() == pixel.getPixelValue(5) && color.getBlue() == pixel.getPixelValue(6)){
                    		pixel.setFound(1);
                    		pixel.setFound(2);
                    	}
                    }
                    
                    red = 255 - color.getRed();
                    green = 255 - color.getGreen();
                    blue = 255 - color.getBlue();
                    
                    if(pixel.getPixelValue(1) == 0 ){
                    	if(color.getRed() == pixel.getPixelValue(4) && color.getGreen() == pixel.getPixelValue(5) && color.getBlue() == pixel.getPixelValue(6)){
                    		pixel.setFound(1);
                    		pixel.setFound(3);
                    	}
                    }
                    
                    out.writeInt(red);
        			out.writeInt(green);
        			out.writeInt(blue);
        			if(i == widthEnd && j == heightEnd){
            			if(widthStart == 0 && heightStart == 0){
            				System.out.println("ImageConsumer: Am transmis primul segment catre WriterResult.");
            			}
            			else if (heightStart == 0){
            				System.out.println("ImageConsumer: Am transmis al doilea segment catre WriterResult.");
            			}
            			else if (widthStart == 0){
            				System.out.println("ImageConsumer: Am transmis al treilea segment catre WriterResult.");
            			}
            			else {
            				System.out.println("ImageConsumer: Am transmis al patrulea segment catre WriterResult.");
            				if(pixel.getPixelValue(1) == 0)
            				{
            					pixel.printHasNotBeenFound();
            				}
            				else{
            					if(pixel.getPixelValue(2) == 1)
            						pixel.printFoundBeforeTrans();
            					if(pixel.getPixelValue(3) == 1)
            						pixel.printFoundAfterTrans();
            				}
            			}
        			}
                }
        	}
            out.close();
            int end = (int)System.currentTimeMillis();
    		if(widthStart == 0 && heightStart == 0){
    			timeValues.setExecutionTimeConsumer(1, end-start, 0, 0, 0);
    		}
    		else if (heightStart == 0){
    			timeValues.setExecutionTimeConsumer(2, 0, end-start, 0, 0);
    		}
    		else if (widthStart == 0){
    			timeValues.setExecutionTimeConsumer(3, 0, 0, end-start, 0);
    		}
    		else {
    			timeValues.setExecutionTimeConsumer(4,0, 0, 0, end-start);
    		}
        } catch (IOException e) {
			e.printStackTrace ();
		}    
    }
}
