package packWork;
import java.awt.Color;

public class BufferWriteResult {
	protected Color imageMatrix[][];
	protected boolean available = false;
	
	public void setSize(int width, int height) {
        imageMatrix = new Color[width][];
        for (int i = 0; i < width; i++) {
            imageMatrix[i] = new Color[height];
        }
    }
	 
	public synchronized Color get(int i, int j) {
		while (!available) {
            try {
                wait(); // Asteapta producatorul sa puna o valoare
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notifyAll();
        return imageMatrix[i][j];
	 }
	
    public synchronized void put(Color value, int i, int j) {
    	this.imageMatrix[i][j] = value;
        if (i == (imageMatrix.length-1) && j == (imageMatrix[0].length-1)){ //se amana procesarea pana dupa terminarea
            available = true;                                      			//consumului intregii informatii din imaginea sursa    
        }
        notifyAll();
     }
}
