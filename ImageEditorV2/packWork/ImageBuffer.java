package packWork;

import java.awt.Color;

public class ImageBuffer {
    private volatile Color imageMatrix[][];		// imaginea din input este sparta in X*Y obiecte de tipul Color si memorata intr-o matrice
    private volatile boolean available = false;
    private int threadsDone;
    private int threadsDoneP;
    
    public void setSize(int width, int height) {
    	imageMatrix = new Color[width][];
        for (int i = 0; i < width; i++) {
            imageMatrix[i] = new Color[height];     //alocare memorie
        }
        this.threadsDone = 0;
        this.threadsDoneP = 0;
        this.available = false;
    }

    public void threadDone() {
    	threadsDone++;
    }
    
    public int getNrOfThDone() {
    	return this.threadsDone;
    }
    
    public void threadDoneP() {
    	threadsDoneP++;
    }
    
    public int getNrOfThDoneP() {
    	return this.threadsDoneP;
    }
    
    public synchronized Color get(int i, int j) {
        while (!available) {
            try {
                wait();                             //se asteapta producatorul sa puna o valoare
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notifyAll();
        if(imageMatrix[i][j]==null)
        	System.out.println("Returnez null");
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