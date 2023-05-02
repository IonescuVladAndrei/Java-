package packWork;

abstract class abstractPixelFound{
	abstractPixelFound(){
		System.out.println("abstractPixelFound is created");
	}
	
	void printFoundBeforeTrans(){
		System.out.println("Pixelul a fost gasit inainte de procesare!");
	}
	
	void printFoundAfterTrans(){
		System.out.println("Pixelul a fost gasit dupa procesare!");
	}
	
	void printHasNotBeenFound(){
	System.out.println("Pixelul nu a fost gasit!");
	}
}

public class PixelFound extends abstractPixelFound{
	protected int found = 0;
	protected int foundBefore = 0;
	protected int foundAfter = 0;
	protected int R;
	protected int G;
	protected int B;

	public PixelFound(int R, int G, int B){
		this.R = R;
		this.G = G;
		this.B = B;
	}
	
	public void setFound(int loc){
		if(loc == 1)
			this.found = 1;
		else if (loc == 2)
			this.foundBefore = 1;
		else if (loc == 3)
			this.foundAfter =  1;
		else
			System.out.println("PixelFound: val pentru found gresita!");
	}
	
	public int getPixelValue(int loc){
		if(loc == 1)
			return this.found;
		else if (loc == 2)
			return this.foundBefore;
		else if (loc == 3)
			return this.foundAfter;
		else if(loc == 4)
			return this.R;
		else if(loc == 5)
			return this.G;
		else if (loc == 6)
			return this.B;
		else{
			System.out.println("PixelFound: val pentru culoare gresita!");
			return -1;
		}
	}
	
}

